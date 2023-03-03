package de.hdmstuttgart.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;


public class UITest {

    // make sure your app is in default condition(no drinks consumed, no default drinks deleted, no own drinks created)
    // but with accepted permissions for camera and storage

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        // press btnProfile
        onView(withId(R.id.btnProfile))
                .perform(click());

        // wait 1s
        wait(1);

        // do input weight : "80"
        onView(withId(R.id.inputWeight))
                .perform(replaceText("80"));

        // press radio button for male
        onView(withId(R.id.radio_male))
                .perform(click());

        // wait 1s
        wait(1);

        // press btnSubmit
        onView(withId(R.id.btnSubmitProfile))
                .perform(click());

        // wait 1s
        wait(1);

        // press btnPlus
        onView(withId(R.id.btnPlus))
                .perform(click());

        // wait 1s
        wait(1);

        //press fabCreateNewDrink
        onView(withId(R.id.fabCreateNewDrink))
                .perform(click());

        // wait 1s
        wait(1);

        // press btnOpenCamera
        onView(withId(R.id.btnOpenCamera))
                .perform(click());

        // wait 1s
        wait(1);

        // take picture
        onView(withId(R.id.captureImg))
                .perform(click());

        // wait 1s
        wait(1);

        // do input in name : "U-Boot"
        onView(withId(R.id.inputNameOwnDrink))
                .perform(replaceText("U-Boot"));

        // input in amount : "300"
        onView(withId(R.id.inputAmountOwnDrink))
                .perform(replaceText("300"));

        // input in volume : "20"
        onView(withId(R.id.inputVolumeOwnDrink))
                .perform(replaceText("20"));

        // wait 1s
        wait(1);

        // press btnConfirmNewDrink
        onView(withId(R.id.btnConfirmNewDrink))
                .perform(click());

        // wait 1s
        wait(1);

        // click on U-Boot
        onView(withText("U-Boot"))
                .perform(click());

        // wait 1s
        wait(1);

        // press btnSubmit
        onView(withId(R.id.btnSubmitDrinkOverview))
                .perform(click());

        // wait 1s
        wait(1);

        // press btnPlus
        onView(withId(R.id.btnPlus))
                .perform(click());

        // wait 1s
        wait(1);

        // click on beer
        onView(withText("Beer"))
                .perform(click());

        // wait 1s
        wait(1);

        // press btnSubmit
        onView(withId(R.id.btnSubmitDrinkOverview))
                .perform(click());

        // wait 1s
        wait(1);

        // press btnPlus
        onView(withId(R.id.btnPlus))
                .perform(click());

        // wait 1s

        // click on wine
        onView(withText("Wine"))
                .perform(click());

        // wait 1s

        // do input in amount : "125"
        onView(withId(R.id.inputAmount))
                .perform(replaceText("125"));

        // do inout in volume : "14.5"
        onView(withId(R.id.inputVolume))
                .perform(replaceText("14.5"));

        // wait 1s
        wait(1);

        // press btnSubmit
        onView(withId(R.id.btnSubmitDrinkOverview))
                .perform(click());

        // wait 1s
        wait(1);

        // press btnPlus
        onView(withId(R.id.btnPlus))
                .perform(click());

        // wait 1s
        wait(1);

        // click on vodka
        onView(withText("Vodka"))
                .perform(click());

        // wait 1s
        wait(1);

        // press btnSubmit
        onView(withId(R.id.btnSubmitDrinkOverview))
                .perform(click());

        // wait 1s
        wait(1);

        // check if tvAlcoholLevel displays "1.92 ‰"
        onView(withId(R.id.tvAlcoholLevel))
                .check(matches(withText("1.92 ‰")));

        // press btnShowDrinksDrunk
        onView(withId(R.id.btnShowDrinksConsumed))
                .perform(click());

        // wait 1s
        wait(1);

        // check if "U-Boot" is displayed
        onView(withId(R.id.rvDrinkDrunkOverview))
                .check(matches(atPosition(0, hasDescendant(withText("U-Boot")))));

        // check if "Beer" is displayed
        onView(withId(R.id.rvDrinkDrunkOverview))
                .check(matches(atPosition(1, hasDescendant(withText("Beer")))));

        // check if "Wine" is displayed
        onView(withId(R.id.rvDrinkDrunkOverview))
                .check(matches(atPosition(2, hasDescendant(withText("Wine")))));

        // check if "Vodka" is displayed
        onView(withId(R.id.rvDrinkDrunkOverview))
                .check(matches(atPosition(3, hasDescendant(withText("Vodka")))));

        // wait 1s
        wait(1);

        // long press on U-Boot
        onView(withText("U-Boot"))
                .perform(longClick());

        // wait 1s
        wait(1);

        // press Delete
        onView(withText("DELETE"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());

        // wait 1s
        wait(1);

        // check if U-Boot is not displayed
        onView(withId(R.id.rvDrinkDrunkOverview))
                .check(matches(not(atPosition(0, hasDescendant(withText("U-Boot"))))));

        // press btnDeleteAll
        onView(withId(R.id.btnDeleteAll))
                .perform(click());

        //wait 1s
        wait(1);

        // press Delete in dialog
        onView(withText("DELETE"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());

        // wait 1s
        wait(1);

        // check if tvAlcoholLevel displays "0.00 ‰"
        onView(withId(R.id.tvAlcoholLevel))
                .check(matches(withText("0.0 ‰")));

        // press btnPlus
        onView(withId(R.id.btnPlus))
                .perform(click());

        // wait 1s
        wait(1);

        // long click on U-Boot
        onView(withText("U-Boot"))
                .perform(longClick());

        // wait 1s
        wait(1);

        // delete U-Boot
        onView(withText("DELETE"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());
    }

    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    // has no item on such position
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }

    public void wait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

