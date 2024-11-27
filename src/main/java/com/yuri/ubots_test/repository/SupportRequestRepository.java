package com.yuri.ubots_test.repository;


import com.yuri.ubots_test.model.SupportRequest;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class SupportRequestRepository extends AbstractRepository<Long, SupportRequest> {

    public Optional<SupportRequest> getByPublicId(UUID publicId) {
        return set.stream().filter(supportRequest -> supportRequest.getPublicId().equals(publicId)).findFirst();
    }

}
