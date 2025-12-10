import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;
import praktikum.IngredientType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    private final static float PRICE_BUN = 2.78f;
    private final static float PRICE_INGREDIENT = 2.78f;

    private final static String NAME_BUN = "black bun";
    private final static String NAME_INGREDIENT = "hot sauce";

    @Mock
    Bun bun;
    @Mock
    Ingredient ingredient;

    @Test
    public void testAddOneIngredientResultsInOneItem() {
       Ingredient ingredient1 = new Ingredient(IngredientType.SAUCE, "", 2.78f);

        Burger burger = new Burger();
        burger.addIngredient(ingredient1);
        assertEquals(1, burger.ingredients.size());
    }
    @Test
    public void testAddNullIngredientResultsInOneItem() {

        Burger burger = new Burger();
        burger.addIngredient(null);
        assertEquals(1, burger.ingredients.size());
    }

    @Test
    public void testSetNullBunResultsNullInBun() {

        Burger burger = new Burger();
        burger.setBuns(null);
        assertNull(burger.bun);
    }
    @Test
    public void testSetBunResultsBun() {
        Bun bun1 = new Bun( "", 2.78f);

        Burger burger = new Burger();
        burger.setBuns(bun1);

        assertEquals(bun1, burger.bun);
    }

    @Test
    public void testRemoveOneExistingIngredientResultsInListSize0() {
        Burger burger = new Burger();
        Ingredient ingredient1 = new Ingredient(IngredientType.SAUCE, "", 2.78f);
        burger.addIngredient(ingredient1);
        burger.removeIngredient(0);

        assertEquals(0, burger.ingredients.size());
    }
    @Test
    public void testRemoveNotExistingIngredientResultsIn() {
        Burger burger = new Burger();
        assertThrows(IndexOutOfBoundsException.class, () -> burger.removeIngredient(0));
    }

    @Test
    public void testMoveExistingInExistingResultsInPositive() {
        Burger burger = new Burger();
        Ingredient ingredient0 = new Ingredient(IngredientType.SAUCE, "", 2.78f);
        burger.addIngredient(ingredient0);
        Ingredient ingredient1 = new Ingredient(IngredientType.SAUCE, "", 2.78f);
        burger.addIngredient(ingredient1);

        burger.moveIngredient(0,1);
       Ingredient expectedIngredient = burger.ingredients.get(1);
        assertEquals(expectedIngredient, ingredient0);
    }

    @Test
    public void testMoveExistingInNonExistingResultsInNegative() {
        Burger burger = new Burger();
        Ingredient ingredient0 = new Ingredient(IngredientType.SAUCE, "", 2.78f);
        burger.addIngredient(ingredient0);

        assertThrows(IndexOutOfBoundsException.class, () -> burger.moveIngredient(0,1));
    }
    @Test
    public void testMoveNonExistingInExistingResultsInNegative() {
        Burger burger = new Burger();
        Ingredient ingredient0 = new Ingredient(IngredientType.SAUCE, "", 2.78f);
        burger.addIngredient(ingredient0);

        assertThrows(IndexOutOfBoundsException.class, () -> burger.moveIngredient(1,0));
    }
    @Test
    public void testGetPriceWhenBurgerIsEmptyThrowsException() {
        Burger burger = new Burger();
        assertThrows(NullPointerException.class, () -> burger.getPrice());
    }
    @Test
    public void testGetPriceWhenHaveBunAndNoIngredientsReturnsPriceOfTwoBun() {
        Burger burger = new Burger();
        Mockito.when(bun.getPrice()).thenReturn(PRICE_BUN);
        burger.setBuns(bun);

        float actualPrice = burger.getPrice();

        assertEquals(PRICE_BUN*2, actualPrice, 0.001);
    }

    @Test
    public void testGetPriceWhenNoBunAndHaveIngredientReturnsPriceOfIngredient() {
        Burger burger = new Burger();

        Mockito.when(ingredient.getPrice()).thenReturn(PRICE_INGREDIENT);
        Mockito.when(bun.getPrice()).thenReturn(PRICE_BUN);
        burger.setBuns(bun);
        burger.addIngredient(ingredient);

        float actualPrice = burger.getPrice();
        float expectedPrice = PRICE_BUN*2 + PRICE_INGREDIENT;
        assertEquals(expectedPrice, actualPrice, 0.001);
    }

    @Test
    public void testGetReceiptWhenNoBunAndNoIngredientThrowsNullPointerException() {
        Burger burger = new Burger();
        assertThrows(NullPointerException.class, () -> burger.getReceipt());
    }

    @Test
    public void testGetReceiptWhenHasBunAndNoIngredientsReturnsReceiptWithoutIngredients() {
        Burger burger = new Burger();
        Mockito.when(bun.getName()).thenReturn(NAME_BUN);
        Mockito.when(bun.getPrice()).thenReturn(PRICE_BUN);
        burger.setBuns(bun);

        String actualReceipt = burger.getReceipt();
        String expectedReceipt ="(==== black bun ====)\r\n" +
                "(==== black bun ====)\r\n" +
                "\r\n" +
                "Price: 5,560000\r\n";

        assertEquals(expectedReceipt, actualReceipt);
    }
    @Test
    public void testGetReceiptWhenHasBunAndHaveIngredientsReturnsReceiptWithBunAndIngredient() {
        Burger burger = new Burger();
        Mockito.when(bun.getName()).thenReturn(NAME_BUN);
        Mockito.when(bun.getPrice()).thenReturn(PRICE_BUN);
        Mockito.when(ingredient.getName()).thenReturn(NAME_INGREDIENT);
        Mockito.when(ingredient.getType()).thenReturn(IngredientType.SAUCE);
        Mockito.when(ingredient.getPrice()).thenReturn(PRICE_INGREDIENT);
        burger.setBuns(bun);
        burger.addIngredient(ingredient);

        String actualReceipt = burger.getReceipt();
        String expectedReceipt ="(==== black bun ====)\r\n" +
                "= sauce hot sauce =\r\n" +
                "(==== black bun ====)\r\n" +
                "\r\n" +
                "Price: 8,340000\r\n";

        assertEquals(expectedReceipt, actualReceipt);
    }

}
