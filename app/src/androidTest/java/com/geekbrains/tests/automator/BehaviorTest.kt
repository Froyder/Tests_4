package com.geekbrains.tests.automator

import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import androidx.test.uiautomator.Until.findObject
import com.geekbrains.tests.*
import com.geekbrains.tests.MAIN_TEXT_VIEW
import com.geekbrains.tests.SEARCH_BUTTON
import com.geekbrains.tests.SEARCH_EDIT_TEXT
import com.geekbrains.tests.TIMEOUT
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class BehaviorTest {

    //Класс UiDevice предоставляет доступ к вашему устройству.
    //Именно через UiDevice вы можете управлять устройством, открывать приложения
    //и находить нужные элементы на экране
    private val uiDevice: UiDevice = UiDevice.getInstance(getInstrumentation())

    //Контекст нам понадобится для запуска нужных экранов и получения packageName
    private val context = setContext()

    //Путь к классам нашего приложения, которые мы будем тестировать
    private val packageName = context.packageName

    @Before
    fun setup() {
        //Для начала сворачиваем все приложения, если у нас что-то запущено
        uiDevice.pressHome()

        //Запускаем наше приложение
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        //Мы уже проверяли Интент на null в предыдущем тесте, поэтому допускаем, что Интент у нас не null
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)//Чистим бэкстек от запущенных ранее Активити
        context.startActivity(intent)

        //Ждем, когда приложение откроется на смартфоне чтобы начать тестировать его элементы
        val wait = uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), TIMEOUT)
    }

    //Убеждаемся, что приложение открыто. Для этого достаточно найти на экране любой элемент
    //и проверить его на null
    @Test
    fun test_MainActivityIsStarted() {
        //Через uiDevice находим editText
        val editText = uiDevice.findObject(By.res(packageName, SEARCH_EDIT_TEXT))
        //Проверяем на null
        Assert.assertNotNull(editText)
    }

    //Убеждаемся, что поиск работает как ожидается
    @Test
    fun test_SearchIsPositive() {
        //Через uiDevice находим editText
        val editText = uiDevice.findObject(By.res(packageName, SEARCH_EDIT_TEXT))
        //Устанавливаем значение
        editText.text = "UiAutomator"

        val searchButton = uiDevice.findObject(By.res(packageName, SEARCH_BUTTON))
        searchButton.click()

        //Ожидаем конкретного события: появления текстового поля mainCountTextView.
        //Это будет означать, что сервер вернул ответ с какими-то данными, то есть запрос отработал.
        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, MAIN_TEXT_VIEW)),
                TIMEOUT
            )
        //Убеждаемся, что сервер вернул корректный результат. Обратите внимание, что количество
        //результатов может варьироваться во времени, потому что количество репозиториев постоянно меняется.
        Assert.assertNotNull(changedText.text.toString())
        Assert.assertEquals(changedText.text.toString(), "Number of results: 690")
    }

    //Убеждаемся, что DetailsScreen открывается
    @Test
    fun test_OpenDetailsScreen() {
        //Находим кнопку
        val toDetails = uiDevice.findObject(By.res(packageName, TO_DETAILS_BUTTON))
        //Кликаем по ней
        toDetails.click()

        //Ожидаем конкретного события: появления текстового поля mainCountTextView.
        //Это будет означать, что DetailsScreen открылся и это поле видно на экране.
        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, DETAILS_TEXT_VIEW)),
                TIMEOUT
            )

        //Убеждаемся, что поле видно и содержит предполагаемый текст.
        //Обратите внимание, что текст должен быть "Number of results: 0",
        //так как мы кликаем по кнопке не отправляя никаких поисковых запросов.
        //Чтобы проверить отображение определенного количества репозиториев,
        //вам в одном и том же методе нужно отправить запрос на сервер и открыть DetailsScreen.
        Assert.assertEquals(changedText.text, TEST_NUMBER_OF_RESULTS_ZERO)
    }

    @Test
    fun test_CheckDetailsData() {

        val editText = uiDevice.findObject(By.res(packageName, SEARCH_EDIT_TEXT))
        editText.text = "UiAutomator"

        val searchButton = uiDevice.findObject(By.res(packageName, SEARCH_BUTTON))
        searchButton.click()

        sleep(TIMEOUT)

        val toDetails = uiDevice.findObject(By.res(packageName, TO_DETAILS_BUTTON))
        toDetails.click()

        val detailsText =
            uiDevice.wait(findObject(By.res(packageName, DETAILS_TEXT_VIEW)),
                TIMEOUT
            )

        Assert.assertEquals(detailsText.text, "Number of results: 690")
    }

    @Test
    fun test_DetailsTextIncrement() {

        val editText = uiDevice.findObject(By.res(packageName, SEARCH_EDIT_TEXT))
        editText.text = "UiAutomator"

        val searchButton = uiDevice.findObject(By.res(packageName, SEARCH_BUTTON))
        searchButton.click()

        sleep(TIMEOUT)

        val toDetails = uiDevice.findObject(By.res(packageName, TO_DETAILS_BUTTON))
        toDetails.click()

        val incrementButton =
            uiDevice.wait(findObject(By.res(packageName, INCREMENT_BUTTON)), TIMEOUT)
        incrementButton.click()

        val detailsText =
            uiDevice.wait(findObject(By.res(packageName, DETAILS_TEXT_VIEW)), TIMEOUT)

        Assert.assertEquals(detailsText.text, "Number of results: 691")
    }

    @Test
    fun test_DetailsTextDecrement() {

        val editText = uiDevice.findObject(By.res(packageName, SEARCH_EDIT_TEXT))
        editText.text = "UiAutomator"

        val searchButton = uiDevice.findObject(By.res(packageName, SEARCH_BUTTON))
        searchButton.click()

        sleep(TIMEOUT)

        val toDetails = uiDevice.findObject(By.res(packageName, TO_DETAILS_BUTTON))
        toDetails.click()

        val decrementButton =
            uiDevice.wait(findObject(By.res(packageName, DECREMENT_BUTTON)), TIMEOUT)
        decrementButton.click()

        val detailsText =
            uiDevice.wait(findObject(By.res(packageName, DETAILS_TEXT_VIEW)), TIMEOUT)

        Assert.assertEquals(detailsText.text, "Number of results: 689")
    }

    @Test
    fun test_EmptyRequest() {
        val searchButton = uiDevice.findObject(By.res(packageName, SEARCH_BUTTON))
        searchButton.click()

        val mainCountTextView =
            uiDevice.wait(findObject(By.res(packageName, MAIN_TEXT_VIEW)), TIMEOUT)

        Assert.assertNull(mainCountTextView)
    }

    @Test
    fun test_ZeroSearchResults() {
        val editText = uiDevice.findObject(By.res(packageName, SEARCH_EDIT_TEXT))
        editText.text = QUASI_REQUEST

        val searchButton = uiDevice.findObject(By.res(packageName, SEARCH_BUTTON))
        searchButton.click()

        sleep(TIMEOUT)

        val mainCountTextView =
            uiDevice.wait(findObject(By.res(packageName, MAIN_TEXT_VIEW)), TIMEOUT)

        Assert.assertEquals(mainCountTextView.text, TEST_NUMBER_OF_RESULTS_ZERO)
    }

    companion object {
        private const val QUASI_REQUEST = "dshfsfdkj7e1927031hjdgsjfku1021838193ikdhsalf"
    }
}