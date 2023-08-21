package com.example.prog3.Service.last1;

import com.example.prog3.Repository.last1.EnterpriseRepository;
import com.example.prog3.model.last1.Enterprise;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EnterpriseService {
  private EnterpriseRepository repository;

  public Enterprise getEnterprise() {
    return repository.findAll().get(0);
  }

  public void updateEnterprise(
      String name,
      String desc,
      String slogan,
      String address,
      String email,
      String nif,
      String stat,
      String rcs,
      byte[] logo) {

    Enterprise enterprise = Enterprise.builder()
        .name(name)
        .description(desc)
        .slogan(slogan)
        .address(address)
        .email(email)
        .nif(nif)
        .stat(stat)
        .rcs(rcs)
        .logo(logo)
        .build();
    repository.save(enterprise);
  }
}
