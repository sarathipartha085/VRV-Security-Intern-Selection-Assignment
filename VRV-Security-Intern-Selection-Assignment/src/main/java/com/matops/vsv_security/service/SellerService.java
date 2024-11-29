package com.matops.vsv_security.service;

import com.matops.vsv_security.model.Seller;
import com.matops.vsv_security.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    public Seller getSellerById(Long id) {
        return sellerRepository.findById(id).orElse(null);
    }
}
