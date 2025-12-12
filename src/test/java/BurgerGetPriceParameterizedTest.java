import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class BurgerGetPriceParameterizedTest {

    private final float bunPrice;
    private final float ingredient1Price;
    private final float ingredient2Price;
    private final float expectedPrice;

    public BurgerGetPriceParameterizedTest(
            float bunPrice,
            float ingredient1Price,
            float ingredient2Price,
            float expectedPrice
    ) {
        this.bunPrice = bunPrice;
        this.ingredient1Price = ingredient1Price;
        this.ingredient2Price = ingredient2Price;
        this.expectedPrice = expectedPrice;
    }

    @Parameterized.Parameters(name = "bun={0}, ing1={1}, ing2={2}, total={3}")
    public static Object[][] getPriceData() {
        return new Object[][]{
                {2f, 3f, 3f, 2f * 2 + 3f + 3f},
                {5f, 0f, 0f, 5f * 2},
                {2f, 4f, 0f, 2f * 2 + 4f}
        };
    }

    @Test
    public void testGetPrice() {
        Bun bun = Mockito.mock(Bun.class);
        Ingredient ing1 = Mockito.mock(Ingredient.class);
        Ingredient ing2 = Mockito.mock(Ingredient.class);

        Mockito.when(bun.getPrice()).thenReturn(bunPrice);

        Mockito.when(ing1.getPrice()).thenReturn(ingredient1Price);

        Mockito.when(ing2.getPrice()).thenReturn(ingredient2Price);


        Burger burger = new Burger();
        burger.setBuns(bun);
        burger.addIngredient(ing1);
        burger.addIngredient(ing2);

        float actual = burger.getPrice();

        assertEquals(expectedPrice, actual, 0.0001f);
    }
}
