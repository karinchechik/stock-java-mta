package il.ac.mta.service;

import il.ac.mta.dto.NasdaqSymbolDto;
import il.ac.mta.exception.SymbolNotFoundInNasdaq;
import il.ac.mta.model.Stock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.labs.repackaged.com.google.common.collect.Lists;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

/**
 * Handles all network streaming aspects.
 *
 * @author hanan.gitliz@gmail.com
 * @since Jan 5, 2015
 */
public class MarketService {

	private final Logger log = Logger.getLogger(MarketService.class.getSimpleName());

	private static final int STOCK_UPDATE_BATCH_SIZE = 100;

	private static final String $_SYMBOLS = "${symbols}";
	private final String NASDAQ_STOCK_LIST_CSV = "http://www.nasdaq.com/screening/companies-by-name.aspx?letter=0&exchange=nasdaq&render=download";
	private static final String YQL_URL = "http://query.yahooapis.com/v1/public/yql?q=";

	private final String YQL_QUERY = "select * from yahoo.finance.quotes where symbol in (" + $_SYMBOLS + ")";
	private final String YQL_ADDITIONAL = "&env=http://datatables.org/alltables.env&format=json";
	private final String ESCAPING = "\"";

	private static final float SPREAD = 0.03f;
	
	private static MarketService instance = new MarketService();

	private List<NasdaqSymbolDto> nasdaqSymbols;

	public static MarketService getInstance() {
		return instance;
	}

	private MarketService() {}
	
	public Stock getStock(String symbol) throws SymbolNotFoundInNasdaq {
		
		List<Stock> stocks = getStocks(Lists.newArrayList(symbol));
		
		if(stocks.isEmpty()) {
			return null;
		}else {
			return stocks.get(0);
		}
		
	}

	public List<Stock> getStocks(List<String> symbols) throws SymbolNotFoundInNasdaq {

		//save data to tmp list, after update done, point with service list to tmp...
		List<Stock> ret = new LinkedList<Stock>();

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < symbols.size(); i++) {

			//if(i > 100) break;

			sb.append(ESCAPING);
			sb.append(symbols.get(i));
			sb.append(ESCAPING);
			sb.append(",");

			//on batch size limit - send an HTTP request to fetch next symbols batch.
			if(i != 0 && i % STOCK_UPDATE_BATCH_SIZE == 0){

				try {
					ret.addAll(doCall(sb));
				}finally {
					//reset string builder.
					sb = new StringBuilder();
				}
			}
		}
		
		//deal with last batch
		if(sb.length() > 0)
			ret.addAll(doCall(sb));

		return ret;
	}
	
	private List<Stock> doCall(StringBuilder sb) throws SymbolNotFoundInNasdaq {
		List<Stock> ret = new LinkedList<Stock>();
		InputStream is = null;
		try {
			//remove last comma
			sb.deleteCharAt(sb.length()-1);

			//encode url for http client.
			String yql = YQL_QUERY.replace($_SYMBOLS, sb.toString());
			String encoded = URLEncoder.encode(yql, "UTF-8");

			URL url = new URL(YQL_URL + encoded + YQL_ADDITIONAL);
			is = url.openStream();

			//create quote/s json
			StringBuilder jsonSB = new StringBuilder();
			byte[] buffer = new byte[8192];
			int read = -1;
			while ((read = is.read(buffer, 0, 8192)) != -1) {
				for (int j = 0; j < read; j++) {
					jsonSB.append((char)buffer[j]);							
				}
			}

			if(jsonSB.length() > 0) {
				JSONObject json = new JSONObject(jsonSB.toString());
				json = json.getJSONObject("query");
				int count = json.getInt("count");
				JSONObject results = json.getJSONObject("results");

				//Yahoo result is array for more than 1 result and an object to a single result. 
				if(count > 1) {
					JSONArray arr = results.getJSONArray("quote");
					for (int j = 0; j < arr.length(); j++) {
						JSONObject quote = arr.getJSONObject(j);
						
						if(!quote.isNull("ErrorIndicationreturnedforsymbolchangedinvalid")) {
							log.warning("someone tried adding stock which doesn't exists: " + sb.toString());
							throw new SymbolNotFoundInNasdaq(quote.getString("symbol"));
						}
						
						Stock stock = fromJson(quote);
						if(stock != null) ret.add(stock);
					}
				}else if (count > 0) {
					JSONObject quote = results.getJSONObject("quote");
					
					if(!quote.isNull("ErrorIndicationreturnedforsymbolchangedinvalid")) {
						log.warning("someone tried adding stock which doesn't exists: " + sb.toString());
						throw new SymbolNotFoundInNasdaq(quote.getString("symbol"));
					}
					
					Stock stock = fromJson(quote);
					if(stock != null) ret.add(stock);
				}
			}

		}catch (SymbolNotFoundInNasdaq e) {
			throw e;
		}catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
		}finally {
			//close stream.
			if(is != null)
				try {
					is.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
		}
		
		return ret;
	}

	public List<NasdaqSymbolDto> getNasdaqSymbols() {

		if(this.nasdaqSymbols == null || this.nasdaqSymbols.isEmpty()) {
			this.nasdaqSymbols = new ArrayList<NasdaqSymbolDto>();
			
			InputStream inputStream = null;
			
			try {
				URL url = new URL(NASDAQ_STOCK_LIST_CSV);
				URLConnection connection = url.openConnection();
				connection.setConnectTimeout(10000);

				// Get the response
				inputStream = url.openStream();
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader rd = new BufferedReader (inputStreamReader);

				//this is a header line - just drop it.
				rd.readLine();

				//for each line - add symbol to list.
				String line = "";
				while ((line = rd.readLine()) != null) {
					String[] split = line.split(",");

					String symbol = split[0].trim();
					symbol = symbol.replace("\"", "");

					String company = split[1].trim();
					company = company.replace("\"", "");
					
					String industry = split[6].trim();
					industry = industry.replace("\"", "");

					this.nasdaqSymbols.add(new NasdaqSymbolDto(symbol, company, industry));
				}

				log.log(Level.INFO, "Finished fetching symboles.Found " + this.nasdaqSymbols.size() + " symbols.");

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
			}finally {
				if(inputStream != null)
					try {
						inputStream.close();
					} catch (IOException e) {
						log.log(Level.SEVERE, e.getMessage());
					}
			}

		}

		return this.nasdaqSymbols;
	}

	private Stock fromJson(JSONObject stockJson) {
		try {
			Stock ret = new Stock();
			ret.setSymbol(stockJson.getString("Symbol"));
			if(!stockJson.isNull("LastTradePriceOnly")) ret.setAsk((float) stockJson.getDouble("LastTradePriceOnly"));
			if(!stockJson.isNull("Bid")) ret.setBid((float) stockJson.getDouble("Bid"));
			
			if(ret.getAsk() != 0 && ret.getBid() == 0) {
				//create my own spread.
				ret.setBid(ret.getAsk() * (1 - SPREAD));
			}
			
			ret.setDate(new Date());

			return ret;
		} catch (JSONException e) {
			log.log(Level.SEVERE, e.getMessage());
			return null;
		}
	}
}
