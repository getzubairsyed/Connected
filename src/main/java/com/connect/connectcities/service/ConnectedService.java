package com.connect.connectcities.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface ConnectedService {

    public boolean find(String city1,  String city2) throws IOException;
}
