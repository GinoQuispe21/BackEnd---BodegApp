package com.bodegapp.demo.service;

import com.bodegapp.demo.exception.ResourceNotFoundException;
import com.bodegapp.demo.model.Payment;
import com.bodegapp.demo.repository.CustomerAccountRepository;
import com.bodegapp.demo.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    @Override
    public ResponseEntity<?> deletePayment(Long customerAccountId, Long paymentId) {
        return paymentRepository.findByIdAndCustomerAccountId(paymentId, customerAccountId).map(payment -> {
            paymentRepository.delete(payment);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Payment not found with Id" + paymentId + " and CustomerAccountId " + customerAccountId));
    }

    @Override
    public Payment updatePayment(Long customerAccountId, Long paymentId, Payment paymentDetails) {
        if (!customerAccountRepository.existsById(customerAccountId))
            throw new ResourceNotFoundException("Customer", "Id", customerAccountId);
        return paymentRepository.findById(paymentId).map(payment -> {
            payment.setGenerated_date(paymentDetails.getGenerated_date());
            payment.setPayment(paymentDetails.getPayment());
            return paymentRepository.save(payment);
        }).orElseThrow(() -> new ResourceNotFoundException("Payment", "Id", paymentId));
    }

    @Override
    public Payment createPayment(Long customerAccountId, Payment payment) {

        return customerAccountRepository.findById(customerAccountId).map(customerAccount -> {
            payment.setCustomerAccount(customerAccount);

            double capital;
            double year = 0;
            double valorFuturo;
            double interes = 0;
            double cantPeri = 0;

            if(customerAccount.getCurrentBalance() == 0.00){
                return null;
            }
            else {
                capital = customerAccount.getCurrentBalance();

                if (customerAccount.getTypeYear() == 1) {
                    year = 360;
                }
                if (customerAccount.getTypeYear() == 2) {
                    year = 365;
                }

                Date lastDate = payment.getGenerated_date();
                Date firstDate = customerAccount.getFirstDate();

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

                String fd = formatter.format(firstDate);
                String ld = formatter.format(lastDate);

                LocalDate d1 = LocalDate.parse(fd, DateTimeFormatter.ISO_LOCAL_DATE);
                LocalDate d2 = LocalDate.parse(ld, DateTimeFormatter.ISO_LOCAL_DATE);
                Duration diff = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
                double dif = diff.toDays();

                customerAccount.setTestDate(dif);

                if(customerAccount.getInterestRatePeriod() == 1){
                    cantPeri = 30;
                }
                if(customerAccount.getInterestRatePeriod() == 2){
                    cantPeri = 60;
                }
                if(customerAccount.getInterestRatePeriod() == 3){
                    cantPeri = 90;
                }
                if(customerAccount.getInterestRatePeriod() == 4){
                    cantPeri = 120;
                }
                if(customerAccount.getInterestRatePeriod() == 5){
                    cantPeri = 180;
                }
                if(customerAccount.getInterestRatePeriod() == 6){
                    cantPeri = 360;
                }


                if (customerAccount.getInterestRateType() == 1) {
                    //Interes Simple
                    interes = capital * customerAccount.getInterestRate() * (year/cantPeri) * (dif / year);
                }
                if (customerAccount.getInterestRateType() == 2) {

                    //Interes Nominal
                    //60 = m Capitalizacion, caso ejemplo Bimestral
                    interes = capital * (Math.pow((1 + (customerAccount.getInterestRate() / 60)), (dif)) - 1);
                }
                if (customerAccount.getInterestRateType() == 3) {

                    //Interes Efectivo
                    interes = capital * (Math.pow(1 + customerAccount.getInterestRate(), (dif) / (year/cantPeri)) - 1);
                }

                if(payment.getPayment() <= capital + interes){
                    valorFuturo = capital + interes - payment.getPayment();
                    customerAccount.setCurrentBalance(valorFuturo);
                    customerAccount.setFirstDate(payment.getGenerated_date());
                }
                else{
                    return null;
                }
            }

            return paymentRepository.save(payment);
        }).orElseThrow(() -> new ResourceNotFoundException( "CustomerAccountId", "Id", customerAccountId));

    }

    @Override
    public Payment getPaymentByIdAndCustomerId(Long customerAccountId, Long paymentId) {
        return paymentRepository.findByIdAndCustomerAccountId(paymentId, customerAccountId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Payment not found with Id" + paymentId + " and CustomerAccountId " + customerAccountId));
    }

    @Override
    public Page<Payment> getAllPaymentsByCustomerAccountId(Long customerAccountId, Pageable pageable) {
        return paymentRepository.findByCustomerAccountId(customerAccountId, pageable);
    }

    @Override
    public List<Payment> getAllPayments() { return paymentRepository.findAll(); }
}
