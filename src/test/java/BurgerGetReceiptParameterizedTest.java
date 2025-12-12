import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;
import praktikum.IngredientType;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class BurgerGetReceiptParameterizedTest {
    private final String bunName;
    private final float bunPrice;

    private final IngredientType type1;
    private final String name1;
    private final float price1;

    private final IngredientType type2;
    private final String name2;
    private final float price2;

    private final float expectedPrice;

    public BurgerGetReceiptParameterizedTest(String bunName,
                                             float bunPrice,
                                             IngredientType type1,
                                             String name1,
                                             float price1,
                                             IngredientType type2,
                                             String name2,
                                             float price2,
                                             float expectedPrice) {
        this.bunName = bunName;
        this.bunPrice = bunPrice;
        this.type1 = type1;
        this.name1 = name1;
        this.price1 = price1;
        this.type2 = type2;
        this.name2 = name2;
        this.price2 = price2;
        this.expectedPrice = expectedPrice;
    }


    @Parameterized.Parameters(name = "{index}: receipt with bun={0}, ing1={1}, ing2={2}")
    public static Object[][] getReceiptData() {
        return new Object[][]{
                {
                        "White Bun", 100f,
                        IngredientType.SAUCE, "Ketchup", 20f,
                        IngredientType.FILLING, "Beef", 50f,
                        100f * 2 + 20f + 50f
                },
                {
                        "Black Bun", 80f,
                        IngredientType.SAUCE, "Mustard", 10f,
                        IngredientType.SAUCE, "BBQ", 15f,
                        80f * 2 + 10f + 15f
                }
        };
    }

    @Test
    public void testGetReceiptContainsParts() {
        Bun bun = Mockito.mock(Bun.class);
        Ingredient ing1 = Mockito.mock(Ingredient.class);
        Ingredient ing2 = Mockito.mock(Ingredient.class);

        Mockito.when(bun.getName()).thenReturn(bunName);
        Mockito.when(bun.getPrice()).thenReturn(bunPrice);

        Mockito.when(ing1.getName()).thenReturn(name1);
        Mockito.when(ing1.getType()).thenReturn(type1);
        Mockito.when(ing1.getPrice()).thenReturn(price1);

        Mockito.when(ing2.getName()).thenReturn(name2);
        Mockito.when(ing2.getType()).thenReturn(type2);
        Mockito.when(ing2.getPrice()).thenReturn(price2);

        Burger burger = new Burger();
        burger.setBuns(bun);
        burger.addIngredient(ing1);
        burger.addIngredient(ing2);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains(bunName));
        assertTrue(receipt.contains(name1));
        assertTrue(receipt.contains(type1.toString().toLowerCase()));
        assertTrue(receipt.contains(name2));
        assertTrue(receipt.contains(type2.toString().toLowerCase()));

        assertTrue(receipt.contains(String.format("Price: %f", expectedPrice)));
    }
}

