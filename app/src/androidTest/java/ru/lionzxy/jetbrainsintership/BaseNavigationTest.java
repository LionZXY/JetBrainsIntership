package ru.lionzxy.jetbrainsintership;


import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class BaseNavigationTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void baseNavigationTest() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction editText = onView(
                allOf(withId(R.id.search_bar_text), withText("JetBrains"),
                        childAtPosition(
                                allOf(withId(R.id.search_input_parent),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                1)),
                                0),
                        isDisplayed()));
        editText.check(matches(withText("JetBrains")));

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction cardView = onView(
                allOf(withId(R.id.search_list), isDisplayed()));
        cardView.perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textView = onView(
                allOf(withText("JetBrains open source projects."),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                8),
                        isDisplayed()));
        textView.check(matches(withText("JetBrains open source projects.")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.user_link), withText("http://www.jetbrains.com"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                3),
                        isDisplayed()));
        textView2.check(matches(withText("http://www.jetbrains.com")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.user_name), withText("JetBrains"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                1),
                        isDisplayed()));
        textView3.check(matches(withText("JetBrains")));

        pressBack();

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction searchInputView = onView(
                allOf(withId(R.id.search_bar_text),
                        withParent(withId(R.id.search_input_parent)),
                        isDisplayed()));
        searchInputView.perform(replaceText("LionZXY"), closeSoftKeyboard());

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.search_bar_text), withText("LionZXY"),
                        childAtPosition(
                                allOf(withId(R.id.search_input_parent),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                1)),
                                0),
                        isDisplayed()));
        editText2.check(matches(withText("LionZXY")));

        ViewInteraction searchInputView2 = onView(
                allOf(withId(R.id.search_bar_text), withText("LionZXY"),
                        withParent(withId(R.id.search_input_parent)),
                        isDisplayed()));
        searchInputView2.perform(pressImeActionButton());

        ViewInteraction searchButton = onView(
                allOf(withId(R.id.action_search)));
        searchButton.perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.user_name), withText("LionZXY"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.user_card),
                                        0),
                                1),
                        isDisplayed()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //TODO textView4.check(matches(withText("LionZXY")));

        ViewInteraction cardView2 = onView(
                allOf(withId(R.id.search_list), isDisplayed()));
        cardView2.perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        ViewInteraction textView5 = onView(
                allOf(withText("Android-developer"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                8),
                        isDisplayed()));
        textView5.check(matches(withText("Android-developer")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.user_link), withText("https://github.com/LionZXY"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                3),
                        isDisplayed()));
        textView6.check(matches(withText("https://github.com/LionZXY")));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.user_location), withText("Unknown"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                5),
                        isDisplayed()));
        textView7.check(matches(withText("Unknown")));

        pressBack();

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction searchInputView3 = onView(
                allOf(withId(R.id.search_bar_text),
                        withParent(withId(R.id.search_input_parent)),
                        isDisplayed()));
        searchInputView3.perform(replaceText("Tes"), closeSoftKeyboard());

        ViewInteraction searchInputView4 = onView(
                allOf(withId(R.id.search_bar_text), withText("Tes"),
                        withParent(withId(R.id.search_input_parent)),
                        isDisplayed()));
        searchInputView4.perform(pressImeActionButton());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction searchInputView5 = onView(
                allOf(withId(R.id.search_bar_text),
                        withParent(withId(R.id.search_input_parent)),
                        isDisplayed()));
        searchInputView5.perform(replaceText("Jetbrains"), closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction searchInputView6 = onView(
                allOf(withId(R.id.search_bar_text),
                        withParent(withId(R.id.search_input_parent)),
                        isDisplayed()));
        searchInputView6.perform(replaceText("L"), closeSoftKeyboard());

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.body), withText("LionZXY"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.suggestions_list),
                                        0),
                                1),
                        isDisplayed()));
        textView8.check(matches(withText("LionZXY")));

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.suggestions_list),
                        withParent(allOf(withId(R.id.suggestions_list_container),
                                withParent(withId(R.id.search_suggestions_section)))),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textView9 = onView(
                allOf(withId(R.id.body), withText("Tes"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.suggestions_list),
                                        2),
                                1),
                        isDisplayed()));
        textView9.check(matches(withText("Tes")));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
