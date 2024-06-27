package com.anke.jpacrud.controller;

import com.anke.jpacrud.entity.Employee;
import com.anke.jpacrud.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private IEmployeeService employeeService;

    /**
     * Bu metot, Spring Framework tarafından otomatik olarak çağrılır.
     * EmployeeService sınıfını enjekte eder.
     * Ayrıca birden fazla sınıfın implementasyonu varsa,
     *      @Qualifier annotation'ı kullanarak hangi sınıfın enjekte edileceğini belirtebiliriz.
     *
     * @param theEmployeeService
     */
    @Autowired
    public EmployeeController(IEmployeeService theEmployeeService) {
        employeeService = theEmployeeService;
    }

    /**
     * Bu metod, bir HTTP isteği geldiğinde şu adımları izler:
     *
     * /list URL'ine yapılan istekler bu metoda yönlendirilir.
     * employeeService kullanarak veritabanından tüm çalışanları alır.
     * Alınan çalışan listesini Model nesnesine ekler.
     * "list-employees" adlı görünümü döndürür,
     *      böylece Spring Framework bu görünümü kullanarak sayfayı render eder ve kullanıcıya gösterir.
     * @param theModel
     * @return
     */
    @GetMapping("/list")
    public String listEmployees(Model theModel) {
        // get the employees from the db
        List<Employee> theEmployees = employeeService.findAll();

        // add to the spring model
        theModel.addAttribute("employees", theEmployees);
        /* theModel nesnesine, "employees" adı altında theEmployees listesini ekler.
         Bu, View katmanında (görünüm) bu verilerin kullanılabilir olmasını sağlar.
         Örneğin, bir JSP veya Thymeleaf şablonunda employees adıyla bu verilere erişilebilir.*/

        return "employees/list-employees";
        /*Bu, metodun döndüreceği değeri belirtir.
        Spring Framework, bu dönen değeri kullanarak hangi görünümün (view) render edileceğini belirler.
        Bu örnekte, "list-employees" adında bir görünüm döndürülür.
        Bu, genellikle list-employees.jsp veya list-employees.html gibi bir şablon dosyasına karşılık gelir.*/
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {
        Employee theEmployee = new Employee();
        theModel.addAttribute("employee", theEmployee);
        return "employees/employee-form";
    }

    @PostMapping("/save")
    public String showFormForAdd(@ModelAttribute("employee") Employee theEmployee) {
        employeeService.save(theEmployee);
        return "redirect:/employees/list";
    }

    @GetMapping("/showFormForUpdate/{employeeId}")
    public String showFormForUpdate(@ModelAttribute("employeeId") int theId, Model theModel) {
        Employee theEmployee = employeeService.findById(theId);
        theModel.addAttribute("employee", theEmployee);
        return "employees/employee-form";
    }

    @GetMapping("/delete/{employeeId}")
    public String deleteEmployee(@ModelAttribute("employeeId") int theId) {
        employeeService.deleteById(theId);
        return "redirect:/employees/list";
    }
}
