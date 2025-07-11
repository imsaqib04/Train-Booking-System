//package com.saqib.Payment_Service.feign;
//
//import com.saqib.Payment_Service.dto.TrainDto;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//@FeignClient(name = "train-service", path = "/api/train")
//public interface TrainClient {
//
//    @GetMapping("/{trainId}")
//    TrainDto getByTrainId(@PathVariable("trainId") Long trainId);
//}
