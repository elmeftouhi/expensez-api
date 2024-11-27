package com.elmeftouhi.expensez;

import com.elmeftouhi.expensez.expense.Expense;
import com.elmeftouhi.expensez.expense.ExpenseRepository;
import com.elmeftouhi.expensez.expensecategory.ExpenseCategory;
import com.elmeftouhi.expensez.expensecategory.ExpenseCategoryRepository;
import com.elmeftouhi.expensez.expensecategory.ExpenseCategoryStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
@ConditionalOnProperty(name = "feature.use-mocks", havingValue = "true")
public class ExpenseInitializer implements CommandLineRunner {

    private final ExpenseRepository expenseRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;

    public ExpenseInitializer(ExpenseRepository expenseRepository, ExpenseCategoryRepository expenseCategoryRepository) {
        this.expenseRepository = expenseRepository;
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("**** Deleting Content ****");

        expenseRepository.deleteAll();
        expenseCategoryRepository.deleteAll();
        Random random = new Random();

        System.out.println("**** Creating Categories ****");

        for (int i = 1; i<= 10; i++){
            expenseCategoryRepository.save(new ExpenseCategory(
                    "Category ( " + i + " )",
                    ExpenseCategoryStatus.ACTIVE
                )
            );
        }

        System.out.println("**** Creating Expenses ****");

        List<ExpenseCategory> allExpenses = expenseCategoryRepository.findAll();

        for (int i = 0; i < 19346; i++ ){
            ExpenseCategory expenseCategory = allExpenses.get(random.nextInt(10));

            double c = 10 + (200 - 10) * random.nextDouble();
            BigDecimal amount = new BigDecimal(c);
            BigDecimal newAmount = amount.setScale(2, RoundingMode.HALF_UP);
            expenseRepository.save(new Expense(
                            newAmount,
                            "Description for " + expenseCategory.getName(),
                            generateRandomDate(),
                            expenseCategory
                    )
            );
        }

        System.out.println("***** Finished feeding database *****");

    }

    LocalDate generateRandomDate(){
        ArrayList<Integer> years = new ArrayList<>(Arrays.asList(2021, 2022, 2023, 2024));

        Random random = new Random();

        int randomYear = years.get(random.nextInt(years.size()));

        int randomMonth = random.nextInt(12) + 1; // 1 to 12
        int randomDay = random.nextInt(LocalDate.of(randomYear, randomMonth, 1).lengthOfMonth()) + 1; // 1 to days in the month

        LocalDate randomDate = LocalDate.of(randomYear, randomMonth, randomDay);

        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //String formattedDate = randomDate.format(formatter);

        // Print the result
        //System.out.println("Random Date: " + formattedDate);
        return randomDate;
    }
}
