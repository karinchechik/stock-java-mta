package com.karin.stock.servlet;

import il.ac.mta.NewStock;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.karin.stock.*;
import com.karin.stock.model.Portfolio;
import com.karin.stock.service.PortfolioService;

public class PortfolioServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		PortfolioService portfolioService = new PortfolioService();
		Portfolio portfolio = portfolioService.getPortfolio();
		NewStock[] stocks = portfolio.getStocks();
		
		resp.setContentType("text/html");
	}
}
