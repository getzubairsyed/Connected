package com.connect.connectcities.controller;

import com.connect.connectcities.service.ConnectedService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class ConnectedController {

    @Autowired
    public ConnectedService connectedService;

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully found connected cities"), @ApiResponse(code = 401, message = "You are not authorized to view the resource"), @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"), @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @ApiOperation(value = "connected", response = String.class)
    @RequestMapping(value = "/findConnectedCities", method = RequestMethod.POST, produces = "application/json")
    public String findConnection(@RequestParam(value = "city1", required = true) String city1,@RequestParam(value = "city2", required = true) String city2, HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean val = false;
        String connected = "No";
        if(StringUtils.isEmpty(city1) || StringUtils.isEmpty(city2)){
            return connected;
        }
        val = connectedService.find(city1,city2);
        if(val){
            connected = "Yes";
        }
        return connected;
    }
}
