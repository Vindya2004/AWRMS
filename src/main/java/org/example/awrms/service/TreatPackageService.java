package org.example.awrms.service;

import org.example.awrms.dto.TreatPackageDTO;

import java.util.List;

public interface TreatPackageService {
    int savePackage(TreatPackageDTO treatPackageDTO);

    List<TreatPackageDTO> getAllPackages();

    int updatePackage(Long id, TreatPackageDTO treatPackageDTO);

    int deletePackage(Long id);
}
