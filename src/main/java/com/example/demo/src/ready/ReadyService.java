package com.example.demo.src.ready;

import com.example.demo.src.home.HomeDao;
import com.example.demo.src.home.HomeProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReadyService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReadyDao readyDao;
    private final ReadyProvider readyProvider;

    @Autowired
    public ReadyService(ReadyDao readyDao, ReadyProvider readyProvider){
        this.readyDao = readyDao;
        this.readyProvider = readyProvider;
    }


}
