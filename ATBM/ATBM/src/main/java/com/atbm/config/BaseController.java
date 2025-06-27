package com.atbm.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public abstract class BaseController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        CustomHttpServletRequestWrapper requestWrapper = new CustomHttpServletRequestWrapper(req);
        switch (req.getMethod().toUpperCase()) {
            case "PATCH":
                doPatch(requestWrapper, resp);
                break;
            case "DELETE":
                doDelete(requestWrapper, resp);
                break;
            case "PUT":
                doPut(requestWrapper, resp);
                break;
            default:
                super.service(req, resp);
        }
    }

    public long getAccountId(HttpServletRequest req) {
        return Long.parseLong(req.getSession().getAttribute("accountId").toString());
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
