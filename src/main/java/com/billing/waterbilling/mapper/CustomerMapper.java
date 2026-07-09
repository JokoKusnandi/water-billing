package com.billing.waterbilling.mapper;

import com.billing.waterbilling.config.CentralMapperConfig;
import com.billing.waterbilling.dto.request.CreateCustomerRequest;
import com.billing.waterbilling.dto.request.UpdateCustomerRequest;
import com.billing.waterbilling.dto.response.CustomerResponse;
import com.billing.waterbilling.entity.Customer;
import org.mapstruct.*;

@Mapper(config = CentralMapperConfig.class)
public interface CustomerMapper {

    /**
     * Create Entity
     */
    @BeanMapping(ignoreByDefault = true)
    @Mappings({

            @Mapping(target = "customerId",
                    source = "customerId"),

            @Mapping(target = "customerName",
                    source = "customerName"),

            @Mapping(target = "email",
                    source = "email"),

            @Mapping(target = "phone",
                    source = "phone"),

            @Mapping(target = "address",
                    source = "address"),

            @Mapping(target = "meters",
                    ignore = true),

            @Mapping(target = "version",
                    ignore = true),

            @Mapping(target = "createdAt",
                    ignore = true),

            @Mapping(target = "updatedAt",
                    ignore = true)

    })
    Customer toEntity(CreateCustomerRequest request);

    /**
     * Entity -> Response
     */
    @BeanMapping(ignoreByDefault = true)
    @Mappings({

            @Mapping(target = "customerId",
                    source = "customerId"),

            @Mapping(target = "customerName",
                    source = "customerName"),

            @Mapping(target = "email",
                    source = "email"),

            @Mapping(target = "phone",
                    source = "phone"),

            @Mapping(target = "address",
                    source = "address")

    })
    CustomerResponse toResponse(Customer entity);

    /**
     * Update Entity
     */
    @BeanMapping(
            ignoreByDefault = true,
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mappings({

            @Mapping(target = "customerName",
                    source = "customerName"),

            @Mapping(target = "email",
                    source = "email"),

            @Mapping(target = "phone",
                    source = "phone"),

            @Mapping(target = "address",
                    source = "address"),

            @Mapping(target = "meters",
                    ignore = true),

            @Mapping(target = "version",
                    ignore = true),

            @Mapping(target = "createdAt",
                    ignore = true),

            @Mapping(target = "updatedAt",
                    ignore = true)

    })
    void updateEntity(@MappingTarget Customer entity,
                      UpdateCustomerRequest request);

}
