package com.anke.jpacrud.dao;

import com.anke.jpacrud.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Bu interface, JpaRepository interface'sinden türetilmiştir.
 * JpaRepository interface'i, CRUD işlemlerini gerçekleştirmek için gerekli olan tüm metotları içerir.
 * JpaRepository interface'i, Entity sınıfı ve Entity'nin primary key veri tipini parametre olarak alır.
 */
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    /**
     * Bu metot, çalışanları soyadlarına göre sıralar.
     * Başka bir yerde implementasyonu olmadan bu metot, JpaRepository interface'inden gelir.
     * Custom metotlar eklemek için, JpaRepository interface'i genellikle yeterli olur.
     * @return
     */
    List<Employee> findAllByOrderByLastNameAsc();
}
