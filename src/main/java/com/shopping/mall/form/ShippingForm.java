package com.shopping.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ShippingForm {

    @NotBlank
    private String receiverName;

    @NotBlank
    private String receiverMobile;

    @NotBlank
    private String receiverPhone;

    @NotBlank
    private String receiverProvince;

    @NotBlank
    private String receiverDistrict;

    @NotBlank
    private String receiverAddress;

    @NotBlank
    private String receiverCity;

    @NotBlank
    private String receiverZip;
}
