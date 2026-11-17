
package dev.devora.studio

import android.app.Activity
import android.os.Bundle
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.widget.*

class MainActivity : Activity() {

    private val bg = Color.rgb(10, 10, 16)
    private val panel = Color.rgb(22, 21, 32)
    private val purple = Color.rgb(153, 92, 255)
    private val white = Color.WHITE
    private val muted = Color.rgb(160, 157, 177)

    private lateinit var root: LinearLayout
    private lateinit var content: LinearLayout
    private lateinit var prefs: android.content.SharedPreferences

    private data class Lesson(
        val language: String,
        val title: String,
        val level: String,
        val description: String,
        val explanation: String,
        val example: String,
        val question: String,
        val answers: List<String>,
        val correct: Int
    )

    private val lessons = listOf(
        Lesson(
            "Python", "مقدمة إلى Python", "مبتدئ",
            "تعرّف على لغة Python وطريقة كتابة أول برنامج.",
            "Python لغة برمجة سهلة القراءة وتُستخدم في تطوير التطبيقات وتحليل البيانات والذكاء الاصطناعي.\n\nيمكنك البدء بها دون معرفة برمجية سابقة.",
            "print(\"Hello World\")",
            "ما وظيفة print في Python؟",
            listOf("طباعة نص", "حذف ملف", "إنشاء صورة", "إيقاف الجهاز"), 0
        ),
        Lesson(
            "Python", "المتغيرات", "مبتدئ",
            "تعلّم تخزين البيانات واستخدامها.",
            "المتغير هو اسم نستخدمه للإشارة إلى قيمة مخزنة.\n\nيمكن أن يحتوي على نص أو رقم أو قيمة منطقية.",
            "name = \"Ali\"\nage = 18\nprint(name)",
            "أي سطر ينشئ متغيراً؟",
            listOf("print()", "name = \"Ali\"", "if", "return"), 1
        ),
        Lesson(
            "Kotlin", "مقدمة إلى Kotlin", "مبتدئ",
            "تعرّف على لغة تطوير تطبيقات Android.",
            "Kotlin لغة حديثة تعمل على منصة JVM وتُستخدم على نطاق واسع في تطوير تطبيقات Android.\n\nتتميز بوضوح الصياغة ودعم أنواع البيانات الآمنة.",
            "fun main() {\n    println(\"Hello\")\n}",
            "ما الكلمة المستخدمة لتعريف دالة في Kotlin؟",
            listOf("function", "define", "fun", "method"), 2
        ),
        Lesson(
            "Kotlin", "المتغيرات", "مبتدئ",
            "افهم الفرق بين val و var.",
            "نستخدم val لتعريف قيمة لا يمكن إعادة إسنادها، ونستخدم var لقيمة يمكن تغييرها.",
            "val name = \"Ali\"\nvar age = 18\nage = 19",
            "أي كلمة تسمح بتغيير قيمة المتغير؟",
            listOf("val", "var", "fun", "class"), 1
        ),
        Lesson(
            "Java", "مقدمة إلى Java", "مبتدئ",
            "تعرّف على أساسيات لغة Java.",
            "Java لغة برمجة متعددة الاستخدامات، تعتمد على البرمجة كائنية التوجه وتعمل على JVM.",
            "public class Main {\n    public static void main(String[] args) {\n        System.out.println(\"Hello\");\n    }\n}",
            "ما الأمر المستخدم لطباعة النص؟",
            listOf("echo()", "print()", "System.out.println()", "write()"), 2
        ),
        Lesson(
            "JavaScript", "مقدمة إلى JavaScript", "مبتدئ",
            "تعلّم أساسيات لغة الويب التفاعلية.",
            "JavaScript تُستخدم لإضافة التفاعل إلى صفحات الويب، كما يمكن استخدامها في تطبيقات الخادم.",
            "let name = \"Ali\";\nconsole.log(name);",
            "ما وظيفة console.log؟",
            listOf("عرض قيمة في وحدة التحكم", "حذف الصفحة", "إغلاق المتصفح", "إنشاء قاعدة بيانات"), 0
        ),
        Lesson(
            "C++", "مقدمة إلى C++", "مبتدئ",
            "ابدأ بتعلّم أساسيات C++.",
            "C++ لغة قوية تُستخدم في البرمجيات عالية الأداء والألعاب والأنظمة.",
            "#include <iostream>\nusing namespace std;\n\nint main() {\n    cout << \"Hello\";\n    return 0;\n}",
            "ما المكتبة المستخدمة في المثال؟",
            listOf("iostream", "stdio", "string", "math"), 0
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = bg
        window.navigationBarColor = bg
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL

        prefs = getSharedPreferences("devora_progress", Context.MODE_PRIVATE)

        root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(bg)
            layoutDirection = View.LAYOUT_DIRECTION_RTL
        }

        setContentView(root)
        showHome()
    }

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()

    private fun cardBackground(color: Int = panel): GradientDrawable {
        return GradientDrawable().apply {
            setColor(color)
            cornerRadius = dp(18).toFloat()
            setStroke(dp(1), Color.rgb(48, 40, 65))
        }
    }

    private fun text(
        value: String,
        size: Float = 14f,
        color: Int = white,
        bold: Boolean = false
    ): TextView {
        return TextView(this).apply {
            text = value
            textSize = size
            setTextColor(color)
            typeface = Typeface.create(
                "sans-serif",
                if (bold) Typeface.BOLD else Typeface.NORMAL
            )
            gravity = Gravity.RIGHT
            textDirection = View.TEXT_DIRECTION_RTL
            setLineSpacing(dp(4).toFloat(), 1f)
        }
    }

    private fun button(
        title: String,
        action: () -> Unit,
        primary: Boolean = false
    ): TextView {
        return TextView(this).apply {
            text = title
            textSize = 14f
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
            setTextColor(white)
            background = cardBackground(
                if (primary) Color.rgb(105, 48, 190) else panel
            )
            setPadding(dp(16), dp(13), dp(16), dp(13))
            setOnClickListener { action() }
        }
    }

    private fun addSpace(parent: LinearLayout, height: Int = 12) {
        parent.addView(View(this), LinearLayout.LayoutParams(-1, dp(height)))
    }

    private fun addText(
        parent: LinearLayout,
        value: String,
        size: Float = 14f,
        color: Int = white,
        bold: Boolean = false
    ) {
        parent.addView(
            text(value, size, color, bold),
            LinearLayout.LayoutParams(-1, -2)
        )
    }

    private fun addCard(
        parent: LinearLayout,
        child: View,
        padding: Int = 16
    ) {
        val box = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            background = cardBackground()
            setPadding(dp(padding), dp(padding), dp(padding), dp(padding))
        }
        box.addView(child)
        parent.addView(
            box,
            LinearLayout.LayoutParams(-1, -2).apply {
                bottomMargin = dp(12)
            }
        )
    }

    private fun openPage(title: String) {
        root.removeAllViews()

        val header = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(dp(18), dp(14), dp(18), dp(14))
        }

        val heading = text(title, 19f, white, true)
        header.addView(
            heading,
            LinearLayout.LayoutParams(0, -2, 1f)
        )

        header.addView(button("الرئيسية") { showHome() })
        root.addView(header)

        val scroll = ScrollView(this).apply {
            isFillViewport = true
        }

        content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(18), dp(10), dp(18), dp(24))
        }

        scroll.addView(content)
        root.addView(
            scroll,
            LinearLayout.LayoutParams(-1, 0, 1f)
        )

        val nav = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            setPadding(dp(5), dp(8), dp(5), dp(8))
            setBackgroundColor(Color.rgb(17, 16, 25))
        }

        val items = listOf(
            "الرئيسية" to { showHome() },
            "التعلّم" to { showAcademy() },
            "تقدمي" to { showProgress() }
        )

        items.forEach { (label, action) ->
            nav.addView(
                button(label, action),
                LinearLayout.LayoutParams(0, -2, 1f).apply {
                    setMargins(dp(3), 0, dp(3), 0)
                }
            )
        }

        root.addView(nav)
    }

    private fun completedCount(): Int =
        prefs.getStringSet("completed", emptySet())?.size ?: 0

    private fun totalXp(): Int =
        prefs.getInt("xp", 0)

    private fun showHome() {
        openPage("DEVORA")

        addText(content, "مرحباً بك في", 15f, muted)
        addText(content, "DEVORA ACADEMY", 27f, white, true)
        addSpace(content, 5)
        addText(content, "تعلّم البرمجة. طوّر مهاراتك. اصنع مستقبلك.", 13f, muted)
        addSpace(content, 20)

        val hero = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            background = cardBackground(Color.rgb(37, 22, 58))
            setPadding(dp(20), dp(22), dp(20), dp(22))
        }

        addText(hero, "مساحة تعلّمك البرمجي", 20f, white, true)
        addSpace(hero, 8)
        addText(
            hero,
            "ابدأ من الأساسيات وانتقل تدريجياً إلى المفاهيم المتقدمة.",
            14f,
            Color.rgb(218, 207, 235)
        )
        addSpace(hero, 16)
        hero.addView(button("استكشف الدروس") { showAcademy() }, 
            LinearLayout.LayoutParams(-1, -2))

        addCard(content, hero, 18)
        addText(content, "إحصائيات التعلّم", 18f, white, true)
        addSpace(content, 10)

        val stats = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
        }

        statBox(stats, "الدروس المكتملة", completedCount().toString())
        statBox(stats, "نقاط الخبرة", totalXp().toString())
        content.addView(stats)
        addSpace(content, 18)

        addText(content, "المسارات التعليمية", 18f, white, true)
        addSpace(content, 10)

        listOf(
            "Python" to "تعلم البرمجة وتحليل البيانات",
            "Kotlin" to "تطوير تطبيقات Android",
            "Java" to "البرمجة كائنية التوجه",
            "JavaScript" to "برمجة الويب التفاعلي",
            "C++" to "أساسيات البرمجة عالية الأداء"
        ).forEach { (name, description) ->
            addLanguageCard(name, description)
        }
    }

    private fun statBox(parent: LinearLayout, title: String, value: String) {
        val box = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            background = cardBackground()
            setPadding(dp(12), dp(15), dp(12), dp(15))
        }
        addText(box, value, 23f, purple, true)
        addSpace(box, 5)
        addText(box, title, 12f, muted)
        parent.addView(
            box,
            LinearLayout.LayoutParams(0, -2, 1f).apply {
                setMargins(dp(4), 0, dp(4), 0)
            }
        )
    }

    private fun addLanguageCard(name: String, description: String) {
        val box = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            background = cardBackground()
            setPadding(dp(16), dp(15), dp(16), dp(15))
        }

        addText(box, name, 19f, purple, true)
        addSpace(box, 5)
        addText(box, description, 13f, muted)
        addSpace(box, 12)
        box.addView(button("عرض الدروس") {
            showLanguage(name)
        })

        content.addView(
            box,
            LinearLayout.LayoutParams(-1, -2).apply {
                bottomMargin = dp(10)
            }
        )
    }

    private fun showAcademy() {
        openPage("الأكاديمية")
        addText(content, "اختر مسارك التعليمي", 21f, white, true)
        addSpace(content, 8)
        addText(content, "كل مسار يحتوي على دروس واختبارات قصيرة.", 13f, muted)
        addSpace(content, 18)

        val languages = lessons.map { it.language }.distinct()

        languages.forEach { language ->
            val count = lessons.count { it.language == language }
            val box = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                background = cardBackground()
                setPadding(dp(17), dp(17), dp(17), dp(17))
            }

            addText(box, language, 20f, purple, true)
            addSpace(box, 5)
            addText(box, "$count دروس تعليمية", 13f, muted)
            addSpace(box, 12)
            box.addView(button("دخول المسار") {
                showLanguage(language)
            })

            addCard(content, box)
        }
    }

    private fun showLanguage(language: String) {
        openPage(language)

        val selected = lessons.filter { it.language == language }

        addText(content, "مسار $language", 22f, white, true)
        addSpace(content, 8)
        addText(content, "اختر درساً للبدء بالتعلّم.", 13f, muted)
        addSpace(content, 18)

        selected.forEach { lesson ->
            val index = lessons.indexOf(lesson)
            val done = isCompleted(index)

            val box = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                background = cardBackground()
                setPadding(dp(17), dp(16), dp(17), dp(16))
            }

            addText(box, lesson.title, 17f, white, true)
            addSpace(box, 6)
            addText(box, "${lesson.level}  •  $language", 12f, purple)
            addSpace(box, 7)
            addText(box, lesson.description, 13f, muted)
            addSpace(box, 12)
            box.addView(
                button(if (done) "مكتمل ✓" else "ابدأ الدرس") {
                    showLesson(index)
                }, LinearLayout.LayoutParams(-1, -2)
            )

            addCard(content, box)
        }
    }

    private fun showLesson(index: Int) {
        val lesson = lessons[index]
        openPage("الدرس")

        addText(content, lesson.language.uppercase(), 12f, purple, true)
        addSpace(content, 8)
        addText(content, lesson.title, 24f, white, true)
        addSpace(content, 10)
        addText(content, lesson.level, 12f, purple)
        addSpace(content, 18)

        addText(content, "شرح الدرس", 18f, white, true)
        addSpace(content, 8)
        addText(content, lesson.explanation, 15f, muted)
        addSpace(content, 20)

        addText(content, "مثال برمجي", 18f, white, true)
        addSpace(content, 8)

        val code = TextView(this).apply {
            text = lesson.example
            textSize = 14f
            setTextColor(Color.rgb(211, 183, 255))
            typeface = Typeface.MONOSPACE
            textDirection = View.TEXT_DIRECTION_LTR
            gravity = Gravity.LEFT
            setPadding(dp(16), dp(18), dp(16), dp(18))
            background = cardBackground(Color.rgb(15, 14, 23))
        }

        addCard(content, code)
        addSpace(content, 10)

        content.addView(button("انتقل إلى الاختبار") {
            showQuiz(index)
        })
    }

    private fun showQuiz(index: Int) {
        val lesson = lessons[index]
        openPage("اختبار الدرس")

        addText(content, lesson.title, 20f, white, true)
        addSpace(content, 15)
        addText(content, lesson.question, 17f, white, true)
        addSpace(content, 18)

        var answered = false

        lesson.answers.forEachIndexed { answerIndex, answer ->
            content.addView(
                button(answer) {
                    if (answered) return@button
                    answered = true

                    if (answerIndex == lesson.correct) {
                        if (!isCompleted(index)) {
                            markCompleted(index)
                        }
                        showResult(true, lesson, index)
                    } else {
                        showResult(false, lesson, index)
                    }
                },
                LinearLayout.LayoutParams(-1, -2).apply {
                    bottomMargin = dp(10)
                }
            )
        }
    }

    private fun showResult(
        correct: Boolean,
        lesson: Lesson,
        index: Int
    ) {
        openPage("نتيجة الاختبار")

        addText(
            content,
            if (correct) "إجابة صحيحة!" else "إجابة غير صحيحة",
            24f,
            if (correct) Color.rgb(91, 220, 155) else Color.rgb(255, 115, 125),
            true
        )

        addSpace(content, 12)

        addText(
            content,
            if (correct)
                "أحسنت! أكملت هذا الدرس وحصلت على نقاط الخبرة."
            else
                "راجع شرح الدرس وحاول مرة أخرى.",
            14f,
            muted
        )

        addSpace(content, 18)

        if (correct) {
            addText(content, "+50 XP", 25f, purple, true)
        } else {
            addText(
                content,
                "الإجابة الصحيحة: ${lesson.answers[lesson.correct]}",
                14f,
                white,
                true
            )
        }

        addSpace(content, 20)

        content.addView(button("إعادة الاختبار") {
            showQuiz(index)
        })

        addSpace(content, 10)

        content.addView(button("العودة إلى الدروس") {
            showLanguage(lesson.language)
        })
    }

    private fun isCompleted(index: Int): Boolean {
        return prefs.getStringSet("completed", emptySet())
            ?.contains(index.toString()) == true
    }

    private fun markCompleted(index: Int) {
        val completed = prefs.getStringSet(
            "completed",
            emptySet()
        )?.toMutableSet() ?: mutableSetOf()

        completed.add(index.toString())

        prefs.edit()
            .putStringSet("completed", completed)
            .putInt("xp", totalXp() + 50)
            .apply()
    }

    private fun showProgress() {
        openPage("تقدمي")

        val completed = completedCount()
        val total = lessons.size
        val percent = (completed * 100) / total

        addText(content, "رحلتك التعليمية", 22f, white, true)
        addSpace(content, 8)
        addText(content, "تابع ما أنجزته في مسارات DEVORA.", 13f, muted)
        addSpace(content, 20)

        val box = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            background = cardBackground()
            setPadding(dp(18), dp(18), dp(18), dp(18))
        }

        addText(box, "$percent%", 34f, purple, true)
        addSpace(box, 8)
        addText(box, "نسبة إكمال جميع الدروس", 13f, muted)
        addSpace(box, 15)

        val progress = ProgressBar(
            this,
            null,
            android.R.attr.progressBarStyleHorizontal
        ).apply {
            max = 100
            this.progress = percent
            progressTintList = android.content.res.ColorStateList.valueOf(purple)
            progressBackgroundTintList =
                android.content.res.ColorStateList.valueOf(Color.DKGRAY)
        }

        box.addView(progress, LinearLayout.LayoutParams(-1, dp(9)))
        addSpace(box, 15)
        addText(box, "$completed من $total دروس مكتملة", 14f, white, true)

        addCard(content, box)

        addText(content, "نقاط الخبرة", 17f, white, true)
        addSpace(content, 8)
        addText(content, "${totalXp()} XP", 27f, purple, true)

        addSpace(content, 20)
        addText(content, "حالة المسارات", 18f, white, true)
        addSpace(content, 10)

        lessons.map { it.language }.distinct().forEach { language ->
            val languageLessons = lessons.withIndex()
                .filter { it.value.language == language }

            val done = languageLessons.count { isCompleted(it.index) }
            addText(content, "$language  —  $done / ${languageLessons.size}", 14f, muted)
            addSpace(content, 9)
        }
    }
}
