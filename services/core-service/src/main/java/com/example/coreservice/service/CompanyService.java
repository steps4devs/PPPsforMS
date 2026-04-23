package com.example.coreservice.service;

import com.example.coreservice.entity.Company;
import com.example.coreservice.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Transactional(readOnly = true)
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Company findById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    @Transactional
    public Company save(Company company) {
        return companyRepository.save(company);
    }

    @Transactional
    public Company update(Long id, Company companyDetails) {
        Company company = findById(id);
        if (company != null) {
            company.setCompanyName(companyDetails.getCompanyName());
            company.setRuc(companyDetails.getRuc());
            company.setAddress(companyDetails.getAddress());
            company.setPhone(companyDetails.getPhone());
            company.setEmail(companyDetails.getEmail());
            company.setContactPerson(companyDetails.getContactPerson());
            company.setActive(companyDetails.getActive());
            return companyRepository.save(company);
        }
        return null;
    }

    @Transactional
    public void delete(Long id) {
        companyRepository.deleteById(id);
    }
}
