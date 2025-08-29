package main.java.com.example.webhooksql.logic;

import org.springframework.stereotype.Component;

@Component
public class SqlBuilder {

    public String buildForRegNo(String regNo) {
        int lastTwo = parseLastTwo(regNo);
        boolean isOdd = (lastTwo % 2) == 1;
        return isOdd ? question1() : question2();
    }

    private int parseLastTwo(String regNo) {
        // extract last two digits anywhere in the string; if not found, default to 1
        String digits = regNo.replaceAll("\\D+", "");
        if (digits.length() >= 2) {
            return Integer.parseInt(digits.substring(digits.length() - 2));
        } else if (digits.length() == 1) {
            return Integer.parseInt(digits);
        }
        return 1; // safe odd default
    }

    /**
     * Question 1: Younger employees count within department; order by EMP_ID DESC.
     */
    private String question1() {
        return ""
            + "SELECT e.EMP_ID, \n"
            + "       e.FIRST_NAME, \n"
            + "       e.LAST_NAME, \n"
            + "       d.DEPARTMENT_NAME, \n"
            + "       COUNT(e2.EMP_ID) AS YOUNGER_EMPLOYEES_COUNT \n"
            + "FROM EMPLOYEE e \n"
            + "JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID \n"
            + "LEFT JOIN EMPLOYEE e2 ON e.DEPARTMENT = e2.DEPARTMENT \n"
            + "                     AND e2.DOB > e.DOB \n"
            + "GROUP BY e.EMP_ID, e.FIRST_NAME, e.LAST_NAME, d.DEPARTMENT_NAME \n"
            + "ORDER BY e.EMP_ID DESC;";
    }

    /**
     * Question 2: Example – Calculate total salary credited to each employee,
     * along with their department; order by EMP_ID ASC.
     */
    private String question2() {
        return ""
            + "SELECT e.EMP_ID, \n"
            + "       e.FIRST_NAME, \n"
            + "       e.LAST_NAME, \n"
            + "       d.DEPARTMENT_NAME, \n"
            + "       SUM(p.AMOUNT) AS TOTAL_SALARY \n"
            + "FROM EMPLOYEE e \n"
            + "JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID \n"
            + "JOIN PAYMENTS p ON e.EMP_ID = p.EMP_ID \n"
            + "GROUP BY e.EMP_ID, e.FIRST_NAME, e.LAST_NAME, d.DEPARTMENT_NAME \n"
            + "ORDER BY e.EMP_ID ASC;";
    }
}
