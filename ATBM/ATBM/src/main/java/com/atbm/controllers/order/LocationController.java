package com.atbm.controllers.order;

import com.atbm.config.BaseController;
import com.atbm.mapper.FormMapper;
import com.atbm.models.entity.Province;
import com.atbm.models.wrapper.request.CalculateShippingRequest;
import com.atbm.services.LocationService;
import com.atbm.utils.HttpUtils;
import com.atbm.utils.JsonUtils;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@WebServlet("/locations")
public class LocationController extends BaseController {
    private LocationService locationService;

    @Override
    public void init() throws ServletException {
        locationService = CDI.current().select(LocationService.class).get();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Province> provinces = locationService.getProvinces();
        HttpUtils.setResponseJson(resp, provinces);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CalculateShippingRequest calculateShippingRequest = FormMapper.bind(req.getParameterMap(), CalculateShippingRequest.class);
        double shippingFee = locationService.calculateShippingFee(calculateShippingRequest);
        Map<String, Object> shippingFeeMap = Map.of("shippingFee", shippingFee);
        HttpUtils.setResponseJson(resp, shippingFeeMap);
    }
}
