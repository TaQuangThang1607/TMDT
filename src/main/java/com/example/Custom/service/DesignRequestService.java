package com.example.Custom.service;

import com.example.Custom.model.DesignRequest;
import com.example.Custom.repository.DesignRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class DesignRequestService {
    @Autowired
    private DesignRequestRepository repository;

    public DesignRequest save(DesignRequest request) {
        return repository.save(request);
    }

    public void updateStatus(Long id, String status) {
        DesignRequest request = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Design request not found"));
        request.setStatus(status);
        repository.save(request);
    }

    public Optional<DesignRequest> findById(Long id) {
        return repository.findById(id);
    }

    public List<DesignRequest> findAll() {
        return repository.findAll();
    }
}