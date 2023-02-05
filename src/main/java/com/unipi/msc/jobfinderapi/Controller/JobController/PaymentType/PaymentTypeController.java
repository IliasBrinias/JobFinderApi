package com.unipi.msc.jobfinderapi.Controller.JobController.PaymentType;

import com.unipi.msc.jobfinderapi.Constant.ErrorMessages;
import com.unipi.msc.jobfinderapi.Controller.JobController.Request.PaymentTypeRequest;
import com.unipi.msc.jobfinderapi.Controller.Responses.ErrorResponse;
import com.unipi.msc.jobfinderapi.Model.Job.PaymentType.PaymentType;
import com.unipi.msc.jobfinderapi.Model.Job.PaymentType.PaymentTypeRepository;
import com.unipi.msc.jobfinderapi.Model.Job.PaymentType.PaymentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("job/paymentType")
@RequiredArgsConstructor
public class PaymentTypeController {
    private final PaymentTypeRepository paymentTypeRepository;
    private final PaymentTypeService paymentTypeService;
    @GetMapping
    public ResponseEntity<?> getPaymentTypes() {
        return ResponseEntity.ok(paymentTypeService.getPaymentTypes());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getPaymentTypes(@PathVariable Long id) {
        return ResponseEntity.ok(paymentTypeService.getPaymentTypeById(id));
    }
    @PostMapping
    public ResponseEntity<?> addPaymentType(@RequestBody PaymentTypeRequest request) {
        PaymentType paymentType = PaymentType.builder()
                .type(request.getType())
                .build();
        return ResponseEntity.ok(paymentTypeRepository.save(paymentType));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<?> editPaymentType(@PathVariable Long id, @RequestBody PaymentTypeRequest request) {
        PaymentType paymentType = paymentTypeService.getPaymentTypeById(id).orElse(null);
        if (paymentType == null) return ResponseEntity.badRequest().body(new ErrorResponse(false,ErrorMessages.PAYMENT_TYPE_NOT_FOUND));
        if (request.getType()!=null){
            paymentType.setType(request.getType());
        }
        return ResponseEntity.ok(paymentTypeRepository.save(paymentType));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePaymentType(@PathVariable Long id) {
        if (paymentTypeService.deleteWithId(id) == 0) {
            return ResponseEntity.badRequest().body(new ErrorResponse(false, ErrorMessages.PAYMENT_TYPE_NOT_FOUND));
        }
        return ResponseEntity.ok().build();
    }
}
