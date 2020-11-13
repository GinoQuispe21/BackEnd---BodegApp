package com.bodegapp.demo.service;

import com.bodegapp.demo.exception.ResourceNotFoundException;
import com.bodegapp.demo.model.Customer;
import com.bodegapp.demo.model.CustomerAccount;
import com.bodegapp.demo.repository.CustomerAccountRepository;
import com.bodegapp.demo.repository.CustomerRepository;
import com.bodegapp.demo.repository.OrderRepository;
import com.bodegapp.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    @Override
    public ResponseEntity<?> deleteCustomer(Long userId, Long customerId) {
        return customerRepository.findByIdAndUserId(customerId, userId).map(customer ->{
            customerRepository.delete(customer);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(" Customer not found with Id " + customerId + " and UserId " + userId));
    }

    @Override
    public Customer updateCustomer(Long userId, Long customerId, Customer customerDetail) {
        if(!userRepository.existsById(userId))
            throw new ResourceNotFoundException("User", "Id", userId);

        return customerRepository.findById(customerId).map(customer -> {
            customer.setDni(customerDetail.getDni());
            customer.setCustomerName(customerDetail.getCustomerName());
            customer.setCustomerLastname(customerDetail.getCustomerLastname());
            customer.setAddress(customerDetail.getAddress());
            customer.setDistrict(customerDetail.getDistrict());
            customer.setCountry(customerDetail.getCountry());
            customer.setEmail(customerDetail.getEmail());
            return customerRepository.save(customer);
        }).orElseThrow(() -> new ResourceNotFoundException("Customer", "Id", customerId));
    }

    @Override
    public Customer createCustomer(Long userId, Customer customer) {
        return userRepository.findById(userId).map(user -> {
            customer.setUser(user);
            return customerRepository.save(customer);
        }).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
    }

    @Override
    public Customer getCustomerByIdAndUserId(Long userId, Long customerId){
        return customerRepository.findByIdAndUserId(customerId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found with Id" + customerId +
                                "and UserId " + userId
                ));
    }

    @Override
    public Page<Customer> getAllCustomersByUserId(Long userId, Pageable pageable){
        return customerRepository.findByUserId(userId, pageable);
    }

    /*@Override
    public Customer order(Long customerId, double amount) {

        CustomerAccount customerAccount = customerAccountRepository.findByCustomerId(customerId);

        double capital;
        double year = 0;
        double valorFuturo;
        double interes;
        double cantPeri = 0;


        if(customerAccount.getCurrentBalance() == 0.00){
            customerAccount.setCurrentBalance(amount);
            //Asignar la fecha de la compra como la nueva fecha inicial para el siguiente periodo
        }
        else {

            capital = customerAccount.getCurrentBalance();

            if (customerAccount.getTypeYear() == 1) {
                year = 360;
            }
            if (customerAccount.getTypeYear() == 2) {
                year = 365;
            }

            //Recibir  la fecha del anterior movimiento y recibir la fecha actual
            int fecha_final = 7;
            int fecha_inicial = 0;

            switch (customerAccount.getInterestRatePeriod()) {
                case 1:
                    cantPeri = 30;
                case 2:
                    cantPeri = 60;
                case  3:
                    cantPeri = 90;
                case 4:
                    cantPeri = 120;
                case 5:
                    cantPeri = 180;
                case 6:
                    cantPeri = 360;
            }

            if (amount + customerAccount.getCurrentBalance() <= customerAccount.getCredit()) {
                if (customerAccount.getInterestRateType() == 1) {

                    //Interes Simple
                    interes = capital * customerAccount.getInterestRate() * (year/cantPeri) * (fecha_final - fecha_inicial) / year;
                    valorFuturo = capital + interes + amount;
                    customerAccount.setCurrentBalance(valorFuturo);
                }
                if (customerAccount.getInterestRateType() == 2) {

                    //Interes Nominal
                    /* 60 = m Capitalizacion, caso ejemplo Bimestral
                    interes = capital * (Math.pow((1 + (customerAccount.getInterestRate() / 60)), (fecha_final - fecha_inicial)) - 1);
                    valorFuturo = capital + interes + amount;
                    customerAccount.setCurrentBalance(valorFuturo);
                }
                if (customerAccount.getInterestRateType() == 3) {

                    //Interes Efectivo
                    interes = capital * (Math.pow(1 + customerAccount.getInterestRate(), (fecha_final - fecha_inicial) / (year / cantPeri)) - 1);
                    valorFuturo = capital + interes + amount;
                    customerAccount.setCurrentBalance(valorFuturo);
                }
            }
            else{
                System.out.println("Se excede el credito");
            }

        }
        return;
    }

    @Override
    public Customer payment(Long customerId, double payment) {

        CustomerAccount customerAccount = customerAccountRepository.findByCustomerId(customerId);

        double capital;
        double year = 0;
        double valorFuturo;
        double interes = 0;
        double cantPeri = 0;


        if(customerAccount.getCurrentBalance() == 0.00){
            //No presenta deudas el cliente
            System.out.println("No tiene deudas");
        }
        else {
            capital = customerAccount.getCurrentBalance();

            if (customerAccount.getTypeYear() == 1) {
                year = 360;
            }
            if (customerAccount.getTypeYear() == 2) {
                year = 365;
            }

            //Recibir  la fecha del anterior movimiento y recibir la fecha actual
            int fecha_final = 7;
            int fecha_inicial = 0;

            switch (customerAccount.getInterestRatePeriod()) {
                case 1:
                    cantPeri = 30;
                case 2:
                    cantPeri = 60;
                case  3:
                    cantPeri = 90;
                case 4:
                    cantPeri = 120;
                case 5:
                    cantPeri = 180;
                case 6:
                    cantPeri = 360;
            }

            if (customerAccount.getInterestRateType() == 1) {
                //Interes Simple
                interes = capital * customerAccount.getInterestRate() * (year/cantPeri) * (fecha_final - fecha_inicial) / year;
            }
            if (customerAccount.getInterestRateType() == 2) {

                //Interes Nominal
                /* 60 = m Capitalizacion, caso ejemplo Bimestral
                interes = capital * (Math.pow((1 + (customerAccount.getInterestRate() / 60)), (fecha_final - fecha_inicial)) - 1);
            }
            if (customerAccount.getInterestRateType() == 3) {

                //Interes Efectivo
                interes = capital * (Math.pow(1 + customerAccount.getInterestRate(), (fecha_final - fecha_inicial) / (year / cantPeri)) - 1);
            }

            if((payment <= capital + interes)&& (payment > 0)) {
                valorFuturo = capital + interes - payment;
                customerAccount.setCurrentBalance(valorFuturo);
            }
            else{
                System.out.println("El pago excede la deuda");
            }
        }
        return;
    }*/

}
