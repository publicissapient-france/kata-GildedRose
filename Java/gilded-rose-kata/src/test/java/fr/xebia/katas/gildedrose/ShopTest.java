package fr.xebia.katas.gildedrose;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class ShopTest {

    @Test
    public void should_decrease_by_one_the_quality_and_remaining_sellIn_days_of_regular_items() {

        // Given
        final Item[] items = {
                new Item("+5 Dexterity Vest", 10, 20),
                new Item("Conjured Mana Cake", 3, 6)
        };

        // When
        new Shop(items).updateQuality();

        // Then
        assertThat(items)
                .extracting(item -> item.name, item -> item.sellIn, item -> item.quality)
                .containsExactly(
                        tuple("+5 Dexterity Vest", 9, 19),
                        tuple("Conjured Mana Cake", 2, 5)
                );
    }

    @Test
    public void should_increase_by_one_the_quality_of_products_that_get_better_as_they_age() {

        // Given
        final Item[] items = {
                new Item("Aged Brie", 2, 3),
                new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20)
        };

        // When
        new Shop(items).updateQuality();

        // Then
        assertThat(items)
                .extracting(item -> item.name, item -> item.sellIn, item -> item.quality)
                .containsExactly(
                        tuple("Aged Brie", 1, 4),
                        tuple("Backstage passes to a TAFKAL80ETC concert", 14, 21)
                );
    }

    @Test
    public void should_increase_by_two_the_quality_of_products_that_get_better_as_they_age_when_there_are_10_days_or_less_left() {

        // Given
        final Item[] items = {new Item("Backstage passes to a TAFKAL80ETC concert", 8, 30)};

        // When
        new Shop(items).updateQuality();

        // Then
        assertThat(items)
                .extracting(item -> item.name, item -> item.sellIn, item -> item.quality)
                .containsExactly(tuple("Backstage passes to a TAFKAL80ETC concert", 7, 32));
    }

    @Test
    public void should_increase_by_three_the_quality_of_products_that_get_better_as_they_age_when_there_are_5_days_or_less_left() {

        // Given
        final Item[] items = {new Item("Backstage passes to a TAFKAL80ETC concert", 5, 15)};

        // When
        new Shop(items).updateQuality();

        // Then
        assertThat(items)
                .extracting(item -> item.name, item -> item.sellIn, item -> item.quality)
                .containsExactly(tuple("Backstage passes to a TAFKAL80ETC concert", 4, 18));
    }

    @Test
    public void should_decrease_quality_and_sellIn_of_products_twice_as_fast_when_we_have_passed_sellIn_date() {

        // Given
        final Item[] items = {
                new Item("+5 Dexterity Vest", 0, 20),
                new Item("Conjured Mana Cake", 0, 6)
        };

        // When
        new Shop(items).updateQuality();

        // Then
        assertThat(items)
                .extracting(item -> item.name, item -> item.sellIn, item -> item.quality)
                .containsExactly(
                        tuple("+5 Dexterity Vest", -1, 18),
                        tuple("Conjured Mana Cake", -1, 4)
                );
    }

    @Test
    public void should_update_quality_of_backstage_passes_to_zero_when_we_have_passed_the_sellIn_date() {

        // Given
        final Item[] items = {new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20)};

        // When
        new Shop(items).updateQuality();

        // Then
        assertThat(items)
                .extracting(item -> item.name, item -> item.sellIn, item -> item.quality)
                .containsExactly(tuple("Backstage passes to a TAFKAL80ETC concert", -1, 0));
    }

    @Test
    public void should_not_alter_the_quality_of_Sulfuras() {

        // Given
        final Item[] items = {new Item("Sulfuras, Hand of Ragnaros", 10, 80)};

        // When
        new Shop(items).updateQuality();

        // Then
        assertThat(items)
                .extracting(item -> item.name, item -> item.sellIn, item -> item.quality)
                .containsExactly(tuple("Sulfuras, Hand of Ragnaros", 10, 80));
    }

    @Test
    public void should_not_increase_quality_over_50() {

        // Given
        final Item[] items = {new Item("Aged Brie", 4, 50)};

        // When
        new Shop(items).updateQuality();

        // Then
        assertThat(items)
                .extracting(item -> item.name, item -> item.sellIn, item -> item.quality)
                .containsExactly(tuple("Aged Brie", 3, 50));
    }
}