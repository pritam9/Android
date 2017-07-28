package pritam.com.finalexam;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Pritam on 6/26/16.
 */
public class Person implements Serializable{
    String name;
    long budgetLimit,expenses;
    List<Gifts> myGifts;

    public List<Gifts> getMyGifts() {
        return myGifts;
    }

    public void setMyGifts(List<Gifts> myGifts) {
        this.myGifts = myGifts;
    }

    public Person() {
    }

    public String getName() {
        return name;
    }

    public long getBudgetLimit() {
        return budgetLimit;
    }

    public long getExpenses() {
        return expenses;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBudgetLimit(long budgetLimit) {
        this.budgetLimit = budgetLimit;
    }

    public void setExpenses(long expenses) {
        this.expenses = expenses;
    }
}
