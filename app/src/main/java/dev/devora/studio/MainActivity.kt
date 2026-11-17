
package dev.devora.studio

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

class MainActivity : Activity() {

    private val backgroundColor = Color.rgb(12, 12, 18)
    private val panel = Color.rgb(25, 24, 35)
    private val purple = Color.rgb(139, 75, 235)
    private val white = Color.WHITE
    private val muted = Color.rgb(165, 160, 180)
    private val green = Color.rgb(70, 200, 140)

    private lateinit var root: LinearLayout
    private lateinit var content: LinearLayout

    private val preferences by lazy {
        getSharedPreferences("devora_progress", MODE_PRIVATE)
    }

    private val completed = mutableSetOf<String>()

    data class Lesson(
        val id: String,
        val language: String,
        val title: String,
        val level: String,
        val description: String,
        val code: String,
        val explanation: String,
        val question: String,
        val options: List<String>,
        val correctAnswer: Int
    )

    private val lessons = listOf(

        Lesson(
            "py1", "Python",
            "مقدمة إلى Python",
            "مبتدئ",
            "تعرف على لغة Python واستخداماتها.",
            "print(\"Hello World\")",
            "الأمر print() يعرض النصوص والنتائج على الشاشة.",
            "ما وظيفة print()؟",
            listOf(
                "حذف النص",
                "عرض النص",
                "إغلاق البرنامج",
                "إنشاء ملف"
            ),
            1
        ),

        Lesson(
            "py2", "Python",
            "المتغيرات في Python",
            "مبتدئ",
            "تعلم تخزين البيانات داخل المتغيرات.",
            "name = \"Ali\"\nage = 18\nprint(name)",
            "المتغير يخزن قيمة يمكن استخدامها لاحقاً داخل البرنامج.",
            "أي رمز يستخدم لإسناد قيمة إلى متغير؟",
            listOf("==", "=", "!=", "+"),
            1
        ),

        Lesson(
            "kt1", "Kotlin",
            "مقدمة إلى Kotlin",
            "مبتدئ",
            "تعرف على أساسيات Kotlin.",
            "fun main() {\n    println(\"Hello\")\n}",
            "fun تستخدم لتعريف الدوال، وmain هي نقطة بداية البرنامج.",
            "ما الأمر المستخدم لطباعة النص؟",
            listOf("printLine", "println", "write", "echo"),
            1
        ),

        Lesson(
            "kt2", "Kotlin",
            "المتغيرات في Kotlin",
            "مبتدئ",
            "تعلم تعريف المتغيرات.",
            "val name = \"Ali\"\nvar age = 20",
            "val لمتغير لا يعاد إسناد قيمته، وvar لمتغير يمكن تغيير قيمته.",
            "أي نوع يسمح بتغيير قيمة المتغير؟",
            listOf("val", "var", "fun", "class"),
            1
        ),

        Lesson(
            "ja1", "Java",
            "مقدمة إلى Java",
            "مبتدئ",
            "تعرف على شكل برنامج Java.",
            "public class Main {\n    public static void main(String[] args) {\n        System.out.println(\"Hello\");\n    }\n}",
            "تبدأ برامج Java عادة من الدالة main.",
            "ما الأمر المستخدم لطباعة نص في Java؟",
            listOf(
                "console.log()",
                "System.out.println()",
                "print()",
                "echo()"
            ),
            1
        ),

        Lesson(
            "js1", "JavaScript",
            "مقدمة إلى JavaScript",
            "مبتدئ",
            "تعلم أول أمر في JavaScript.",
            "console.log(\"Hello World\");",
            "console.log() تعرض المعلومات في وحدة التحكم.",
            "ما وظيفة console.log()؟",
            listOf(
                "عرض المعلومات",
                "حذف المتغيرات",
                "إيقاف الجهاز",
                "إنشاء مجلد"
            ),
            0
        ),

        Lesson(
            "cpp1", "C++",
            "مقدمة إلى C++",
            "مبتدئ",
            "تعرف على الهيكل الأساسي للبرنامج.",
            "#include <iostream>\nusing namespace std;\n\nint main() {\n    cout << \"Hello\";\n    return 0;\n}",
            "cout تستخدم لطباعة البيانات على الشاشة.",
            "ما وظيفة cout؟",
            listOf(
                "قراءة ملف",
                "طباعة البيانات",
                "إنشاء متغير",
                "إنهاء البرنامج"
            ),
            1
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        completed.addAll(
            preferences.getStringSet("completed", emptySet()) ?: emptySet()
        )

        showHome()
    }

    private fun dp(value: Int): Int {
        return (value * resources.displayMetrics.density).toInt()
    }

    private fun cardBackground(color: Int): GradientDrawable {
        return GradientDrawable().apply {
            setColor(color)
            cornerRadius = dp(18).toFloat()
            setStroke(dp(1), Color.rgb(48, 43, 65))
        }
    }

    private fun createRoot() {
        root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(backgroundColor)
            layoutDirection = View.LAYOUT_DIRECTION_RTL
        }

        setContentView(root)
    }

    private fun render(
        title: String,
        subtitle: String = ""
    ) {
        createRoot()

        val header = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(20), dp(20), dp(20), dp(15))
        }

        val appName = text(
            "DEVORA",
            13f,
            purple,
            true
        )

        header.addView(appName)

        val heading = text(
            title,
            25f,
            white,
            true
        )

        val headingParams = LinearLayout.LayoutParams(
            -1,
            -2
        )

        headingParams.topMargin = dp(10)
        header.addView(heading, headingParams)

        if (subtitle.isNotEmpty()) {
            val sub = text(
                subtitle,
                13f,
                muted
            )

            val subParams = LinearLayout.LayoutParams(
                -1,
                -2
            )

            subParams.topMargin = dp(5)
            header.addView(sub, subParams)
        }

        root.addView(header)

        val scroll = ScrollView(this).apply {
            fillViewport = true
            isVerticalScrollBarEnabled = false
        }

        content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(18), dp(8), dp(18), dp(28))
        }

        scroll.addView(content)

        root.addView(
            scroll,
            LinearLayout.LayoutParams(
                -1,
                0,
                1f
            )
        )
    }

    private fun text(
        value: String,
        size: Float = 15f,
        color: Int = white,
        bold: Boolean = false
    ): TextView {
        return TextView(this).apply {
            text = value
            textSize = size
            setTextColor(color)
            gravity = Gravity.RIGHT
            textDirection = View.TEXT_DIRECTION_FIRST_STRONG
            if (bold) {
                typeface = Typeface.DEFAULT_BOLD
            }
            setPadding(0, dp(3), 0, dp(3))
        }
    }

    private fun addText(
        value: String,
        size: Float = 15f,
        color: Int = white,
        bold: Boolean = false
    ) {
        content.addView(
            text(value, size, color, bold)
        )
    }

    private fun space(height: Int = 12) {
        val spacer = View(this)

        content.addView(
            spacer,
            LinearLayout.LayoutParams(
                1,
                dp(height)
            )
        )
    }

    private fun card(): LinearLayout {
        return LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutDirection = View.LAYOUT_DIRECTION_RTL
            setPadding(dp(16), dp(15), dp(16), dp(15))
            background = cardBackground(panel)
        }
    }

    private fun addCard(
        view: LinearLayout,
        marginTop: Int = 10
    ) {
        val params = LinearLayout.LayoutParams(
            -1,
            -2
        )

        params.topMargin = dp(marginTop)
        content.addView(view, params)
    }

    private fun button(
        title: String,
        primary: Boolean = false,
        action: () -> Unit
    ): TextView {
        return TextView(this).apply {
            text = title
            textSize = 14f
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
            setTextColor(white)

            background = cardBackground(
                if (primary) purple else panel
            )

            setPadding(
                dp(16),
                dp(13),
                dp(16),
                dp(13)
            )

            setOnClickListener {
                action()
            }
        }
    }

    private fun addButton(
        title: String,
        primary: Boolean = false,
        action: () -> Unit
    ) {
        val params = LinearLayout.LayoutParams(
            -1,
            -2
        )

        params.topMargin = dp(10)

        content.addView(
            button(title, primary, action),
            params
        )
    }

    private fun showHome() {
        val total = lessons.size
        val done = completed.size
        val percent = if (total == 0) 0 else done * 100 / total
        val xp = done * 25

        render(
            "مرحباً بك في DEVORA",
            "منصة تعليم البرمجة بطريقة منظمة."
        )

        val welcome = card()

        welcome.addView(
            text(
                "ابدأ رحلتك البرمجية",
                19f,
                white,
                true
            )
        )

        welcome.addView(
            text(
                "تعلم لغات البرمجة من الأساسيات إلى المفاهيم المتقدمة.",
                13f,
                muted
            )
        )

        welcome.addView(
            text(
                "$percent%  •  $done من $total دروس مكتملة",
                14f,
                green,
                true
            )
        )

        welcome.addView(
            text(
                "$xp XP",
                17f,
                purple,
                true
            )
        )

        addCard(welcome, 8)

        space(12)

        addText("الأقسام التعليمية", 18f, white, true)

        addButton(
            "أكاديمية البرمجة",
            true
        ) {
            showAcademy()
        }

        addButton("تقدمي التعليمي") {
            showProgress()
        }

        space(18)

        addText("اللغات المتاحة", 18f, white, true)

        val languages = listOf(
            "Python",
            "Kotlin",
            "Java",
            "JavaScript",
            "C++"
        )

        languages.forEach { language ->
            val count = lessons.count {
                it.language == language
            }

            val item = card()

            item.addView(
                text(
                    language,
                    17f,
                    purple,
                    true
                )
            )

            item.addView(
                text(
                    "$count دروس تعليمية",
                    12f,
                    muted
                )
            )

            addCard(item)

            addButton("دخول $language") {
                showLanguage(language)
            }
        }
    }

    private fun showAcademy() {
        render(
            "أكاديمية البرمجة",
            "اختر اللغة التي تريد تعلمها."
        )

        val languages = listOf(
            "Python",
            "Kotlin",
            "Java",
            "JavaScript",
            "C++"
        )

        languages.forEach { language ->
            val count = lessons.count {
                it.language == language
            }

            val done = lessons.count {
                it.language == language &&
                    completed.contains(it.id)
            }

            val item = card()

            item.addView(
                text(
                    language,
                    18f,
                    purple,
                    true
                )
            )

            item.addView(
                text(
                    "$done / $count دروس مكتملة",
                    13f,
                    muted
                )
            )

            addCard(item)

            addButton("عرض دروس $language", true) {
                showLanguage(language)
            }
        }

        addButton("العودة للرئيسية") {
            showHome()
        }
    }

    private fun showLanguage(language: String) {
        val languageLessons = lessons.filter {
            it.language == language
        }

        render(
            language,
            "المسار التعليمي • ${languageLessons.size} دروس"
        )

        languageLessons.forEachIndexed { index, lesson ->
            val isDone = completed.contains(lesson.id)

            val item = card()

            item.addView(
                text(
                    "الدرس ${index + 1}",
                    12f,
                    purple,
                    true
                )
            )

            item.addView(
                text(
                    lesson.title,
                    17f,
                    white,
                    true
                )
            )

            item.addView(
                text(
                    lesson.description,
                    13f,
                    muted
                )
            )

            item.addView(
                text(
                    if (isDone) "مكتمل ✓" else lesson.level,
                    12f,
                    if (isDone) green else purple,
                    true
                )
            )

            addCard(item)

            addButton(
                if (isDone) "مراجعة الدرس" else "ابدأ الدرس",
                true
            ) {
                showLesson(lesson)
            }
        }

        addButton("رجوع للأكاديمية") {
            showAcademy()
        }
    }

    private fun showLesson(lesson: Lesson) {
        render(
            lesson.title,
            "${lesson.language} • ${lesson.level}"
        )

        val description = card()

        description.addView(
            text(
                "شرح الدرس",
                17f,
                purple,
                true
            )
        )

        description.addView(
            text(
                lesson.description,
                14f,
                white
            )
        )

        description.addView(
            text(
                lesson.explanation,
                13f,
                muted
            )
        )

        addCard(description, 5)

        space(12)

        addText(
            "مثال برمجي",
            17f,
            white,
            true
        )

        val codeCard = card()

        val codeText = text(
            lesson.code,
            14f,
            Color.rgb(190, 160, 255),
            true
        )

        codeText.typeface = Typeface.MONOSPACE
        codeText.gravity = Gravity.LEFT
        codeText.textDirection = View.TEXT_DIRECTION_LTR

        codeCard.addView(codeText)

        addCard(codeCard)

        space(12)

        addText(
            "اختبر فهمك",
            17f,
            white,
            true
        )

        addButton(
            "ابدأ الاختبار",
            true
        ) {
            showQuiz(lesson)
        }

        addButton("العودة إلى الدروس") {
            showLanguage(lesson.language)
        }
    }

    private fun showQuiz(lesson: Lesson) {
        render(
            "اختبار الدرس",
            lesson.language
        )

        val questionCard = card()

        questionCard.addView(
            text(
                lesson.question,
                18f,
                white,
                true
            )
        )

        addCard(questionCard, 5)

        lesson.options.forEachIndexed { index, option ->

            addButton(option) {
                if (index == lesson.correctAnswer) {
                    completed.add(lesson.id)

                    preferences.edit()
                        .putStringSet(
                            "completed",
                            completed.toSet()
                        )
                        .apply()

                    showQuizResult(
                        lesson,
                        true
                    )
                } else {
                    showQuizResult(
                        lesson,
                        false
                    )
                }
            }
        }

        addButton("رجوع للدرس") {
            showLesson(lesson)
        }
    }

    private fun showQuizResult(
        lesson: Lesson,
        correct: Boolean
    ) {
        render(
            if (correct) "إجابة صحيحة" else "حاول مرة أخرى",
            lesson.title
        )

        val result = card()

        result.addView(
            text(
                if (correct) "أحسنت! إجابتك صحيحة." else "الإجابة غير صحيحة.",
                19f,
                if (correct) green else Color.rgb(255, 110, 110),
                true
            )
        )

        result.addView(
            text(
                if (correct) {
                    "تم تسجيل إكمال الدرس وإضافة 25 نقطة."
                } else {
                    "راجع شرح الدرس وحاول مرة أخرى."
                },
                14f,
                muted
            )
        )

        addCard(result, 12)

        if (correct) {
            addButton(
                "الدرس التالي",
                true
            ) {
                showLanguage(lesson.language)
            }
        } else {
            addButton(
                "إعادة الاختبار",
                true
            ) {
                showQuiz(lesson)
            }
        }

        addButton("مراجعة الشرح") {
            showLesson(lesson)
        }
    }

    private fun showProgress() {
        val total = lessons.size
        val done = completed.size
        val remaining = total - done
        val percent = if (total == 0) 0 else done * 100 / total
        val xp = done * 25

        render(
            "تقدمي التعليمي",
            "تابع إنجازاتك في DEVORA."
        )

        val overview = card()

        overview.addView(
            text(
                "$percent%",
                30f,
                purple,
                true
            )
        )

        overview.addView(
            text(
                "نسبة إكمال الدروس",
                13f,
                muted
            )
        )

        overview.addView(
            text(
                "$done مكتمل • $remaining متبقٍ",
                15f,
                white,
                true
            )
        )

        overview.addView(
            text(
                "$xp XP",
                18f,
                green,
                true
            )
        )

        addCard(overview, 5)

        space(15)

        addText(
            "تفاصيل اللغات",
            18f,
            white,
            true
        )

        val languages = listOf(
            "Python",
            "Kotlin",
            "Java",
            "JavaScript",
            "C++"
        )

        languages.forEach { language ->
            val languageLessons = lessons.filter {
                it.language == language
            }

            val finished = languageLessons.count {
                completed.contains(it.id)
            }

            val item = card()

            item.addView(
                text(
                    language,
                    16f,
                    purple,
                    true
                )
            )

            item.addView(
                text(
                    "$finished من ${languageLessons.size} دروس مكتملة",
                    13f,
                    muted
                )
            )

            addCard(item)
        }

        addButton("العودة للرئيسية") {
            showHome()
        }
    }

    override fun onBackPressed() {
        showHome()
    }
}
