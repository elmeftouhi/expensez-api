package com.elmeftouhi.expensez.expense;

import com.elmeftouhi.expensez.common.util.Util;
import com.elmeftouhi.expensez.exception.ExpenseCategoryNotFoundException;
import com.elmeftouhi.expensez.exception.ExpenseNotFoundException;
import com.elmeftouhi.expensez.exception.TagNotFoundException;
import com.elmeftouhi.expensez.expensecategory.ExpenseCategory;
import com.elmeftouhi.expensez.expensecategory.ExpenseCategoryRepository;
import com.elmeftouhi.expensez.expensetag.Tag;
import com.elmeftouhi.expensez.expensetag.TagRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService{

    private final ExpenseRepository expenseRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final TagRepository tagRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, ExpenseCategoryRepository expenseCategoryRepository, TagRepository tagRepository) {
        this.expenseRepository = expenseRepository;
        this.expenseCategoryRepository = expenseCategoryRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public List<ExpenseResponse> getAll(
            Boolean includeDeleted,
            Boolean onlyDeleted,
            String description,
            Long expenseCategoryId,
            String from,
            String to
    ) {

        List<Expense> expenses;

        // check FROM
        if (from.isBlank()){
            from =  LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue() + "-01";
        }
        Util.isValidDate(from);

        // check TO
        if (to.isBlank()){
            to = LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue() + "-" + LocalDate.now().getDayOfMonth();
        }
        Util.isValidDate(to);


        if(Boolean.TRUE.equals(onlyDeleted)){
            expenses = expenseRepository.findOnlyDeletedBetweenDates(LocalDate.parse(from), LocalDate.parse(to));
        } else if (Boolean.TRUE.equals(includeDeleted)) {
            expenses = expenseRepository.findAllByOrderByDateExpenseDesc(LocalDate.parse(from), LocalDate.parse(to));
        }else {
            expenses = expenseRepository.findAllBetweenDates(LocalDate.parse(from), LocalDate.parse(to));
        }

        if (description != null){
            expenses = expenses
                    .stream()
                    .filter(e -> e.getDescription().contains(description))
                    .toList();
        }

        if (expenseCategoryId != null){
            expenses = expenses
                    .stream()
                    .filter(e -> Objects.equals(e.getExpenseCategory().getId(), expenseCategoryId))
                    .toList();
        }


        return expenses
                .stream()
                .map(ExpenseResponse::new)
                .toList();
    }

    @Override
    public void deleteExpense(Long id, Boolean hardDelete) {
        Optional<Expense> expense = expenseRepository.findById(id);
        if (expense.isPresent()){
            if (hardDelete){
                expenseRepository.delete(expense.get());
            }else {
                expense.get().setDeletedAt(LocalDateTime.now());
                expense.get().setDeletedBy(69);
                expenseRepository.save(expense.get());
            }
        }else
            throw new ExpenseNotFoundException("Expense Not Found");

    }

    @Override
    public void update(Long id, ExpenseResource expenseResource) {
        Optional<Expense> expense = expenseRepository.findById(id);

        if (expense.isPresent()){
            expense.get().setAmount(expenseResource.amount() == null? expense.get().getAmount(): expenseResource.amount());
            expense.get().setDescription(expenseResource.description() == null? expense.get().getDescription(): expenseResource.description());
            expense.get().setDateExpense(expenseResource.expenseDate() == null? expense.get().getDateExpense(): expenseResource.expenseDate());
            if (expenseResource.expenseCategoryId() != null){
                Optional<ExpenseCategory> expenseCategory = expenseCategoryRepository.findExpenseCategoryById(expenseResource.expenseCategoryId());
                expenseCategory.ifPresent(category -> expense.get().setExpenseCategory(category));
            }
            expense.get().setUpdatedAt(LocalDateTime.now());
            expense.get().setUpdatedBy(69L);
            expenseRepository.save(expense.get());
        }else {
            throw new ExpenseNotFoundException("Expense not found");
        }
    }

    @Override
    public void save(ExpenseResource expenseResource) {
        Optional<ExpenseCategory> category = expenseCategoryRepository.findExpenseCategoryById(expenseResource.expenseCategoryId());
        if (category.isEmpty()){
            throw new ExpenseCategoryNotFoundException("Expense Category not found");
        }

        expenseRepository.save(
            new Expense(
                    expenseResource.amount(),
                    expenseResource.description(),
                    expenseResource.expenseDate(),
                    category.get()
            )
        );
    }

    @Override
    public ReportResponse getTotalByMonthForAYear(String from, String to, Long expenseCategoryId) {

        // check FROM
        String safeFrom = Objects.requireNonNullElse(from, "");
        if (safeFrom.isBlank()){
            from =  LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue() + "-01";
        }
        Util.isValidDate(from);

        // check TO
        String safeTo = Objects.requireNonNullElse(to, "");
        if (safeTo.isBlank()){
            to = LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue() + "-" + LocalDate.now().getDayOfMonth();
        }
        Util.isValidDate(to);

        List<Object[]> expenses;
        if (expenseCategoryId != null)
            expenses = expenseRepository.getMonthlyExpensesByYearByCategory(from, to, expenseCategoryId);
        else
            expenses = expenseRepository.getMonthlyExpensesByYear(from, to);

        return new ReportResponse(expenses.stream()
                .collect(Collectors.toMap(
                        row -> ((Number) row[0]).intValue(),   // Month
                        row -> ((Number) row[1]).doubleValue() // Total Amount
                )));
    }

    @Override
    public void toggleTag(Long expenseId, Long tagId) {
        Optional<Expense> expense = expenseRepository.findById(expenseId);
        if (expense.isEmpty()){
            throw new ExpenseNotFoundException("Expense not found");
        }

        Optional<Tag> tag = tagRepository.findById(tagId);
        if (tag.isEmpty()){
            throw new TagNotFoundException("Tag not found");
        }



    }

}
