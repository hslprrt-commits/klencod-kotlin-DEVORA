
package dev.devora.studio

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.widget.*

class MainActivity : Activity() {

    private val bg = Color.rgb(10, 10, 16)
    private val surface = Color.rgb(22, 20, 32)
    private val purple = Color.rgb(151, 71, 255)
    private val white = Color.WHITE
    private val muted = Color.rgb(170, 165, 185)
    private val green = Color.rgb(65, 190, 125)

    private val prefs by lazy {
        getSharedPreferences("devora_learning", MODE_PRIVATE)
    }

    private lateinit var content: LinearLayout

    private val languages = listOf(
        "Python",
        "Kotlin",
        "Java",
        "JavaScript",
        "C++"
    )

    private data class Lesson(
        val id: String,
        val language: String,
        val title: String,
        val description: String,
        val explanation: String,
        val example: String,
        val question: String,
        val options: List<String>,
        val answer: Int,
        val reason: String
    )

    private val lessons = listOf(

        Lesson(
            "py1", "Python", "المتغيرات",
            "تعرّف على تخزين البيانات.",
            "المتغير هو اسم نستخدمه لتخزين قيمة داخل البرنامج. يمكن أن تكون القيمة نصاً أو رقماً أو غير ذلك.",
            """name = "Ali"
age = 15
print(name)
print(age)""",
            "أي كلمة تُستخدم لتعريف متغير في Python؟",
            listOf("var", "let", "لا نحتاج كلمة خاصة", "define"),
            2,
            "في Python لا تحتاج إلى كتابة كلمة var أو let. يكفي كتابة اسم المتغير ثم علامة =."
        ),

        Lesson(
            "py2", "Python", "الطباعة",
            "تعلم عرض النتائج على الشاشة.",
            "نستخدم الدالة print() لعرض النصوص والقيم على الشاشة.",
            """print("Hello World")
print(10 + 5)""",
            "ما وظيفة print()؟",
            listOf("حذف البيانات", "عرض النتائج", "إنشاء متغير", "إيقاف البرنامج"),
            1,
            "الدالة print() تعرض النصوص والقيم في مخرجات البرنامج."
        ),

        Lesson(
            "kt1", "Kotlin", "المتغيرات",
            "تعرّف على val و var.",
            "في Kotlin نستخدم val لإنشاء قيمة لا يمكن إعادة تعيينها، ونستخدم var لقيمة يمكن تغييرها.",
            """val name = "Ali"
var age = 15

age = 16""",
            "أي كلمة تسمح بتغيير قيمة المتغير؟",
            listOf("val", "var", "fun", "class"),
            1,
            "var تسمح بإعادة تعيين القيمة، أما val فلا تسمح بذلك."
        ),

        Lesson(
            "kt2", "Kotlin", "الدوال",
            "تعلم إنشاء Function.",
            "الدالة مجموعة تعليمات تنفذ مهمة محددة. نستخدم الكلمة fun لتعريف دالة في Kotlin.",
            """fun greet() {
    println("Hello")
}

greet()""",
            "ما الكلمة المستخدمة لتعريف دالة؟",
            listOf("function", "def", "fun", "method"),
            2,
            "الكلمة fun هي المستخدمة لتعريف الدوال في Kotlin."
        ),

        Lesson(
            "ja1", "Java", "المتغيرات",
            "تعلم تخزين البيانات في Java.",
            "في Java نحدد نوع البيانات عند تعريف المتغير، مثل int للأعداد الصحيحة و String للنصوص.",
            """int age = 15;
String name = "Ali";

System.out.println(name);""",
            "أي نوع يستخدم لتخزين عدد صحيح؟",
            listOf("String", "int", "boolean", "char[]"),
            1,
            "النوع int يستخدم لتخزين الأعداد الصحيحة."
        ),

        Lesson(
            "ja2", "Java", "الشروط",
            "استخدام if و else.",
            "تسمح الشروط للبرنامج باتخاذ قرار بناءً على نتيجة تعبير منطقي.",
            """int age = 18;

if (age >= 18) {
    System.out.println("Adult");
} else {
    System.out.println("Young");
}""",
            "ما وظيفة if؟",
            listOf("تكرار الكود", "اتخاذ قرار", "تعريف متغير", "حذف ملف"),
            1,
            "if تفحص شرطاً، ثم تنفذ التعليمات إذا كان الشرط صحيحاً."
        ),

        Lesson(
            "js1", "JavaScript", "المتغيرات",
            "تعلم let و const.",
            "تستخدم JavaScript الكلمة let للمتغيرات القابلة للتغيير، وconst للقيم التي لا يمكن إعادة تعيينها.",
            """let age = 15;
const name = "Ali";

age = 16;""",
            "أي كلمة تنشئ متغيراً قابلاً للتغيير؟",
            listOf("const", "let", "static", "fixed"),
            1,
            "let تسمح بتغيير قيمة المتغير بعد تعريفه."
        ),

        Lesson(
            "js2", "JavaScript", "الطباعة",
            "عرض النتائج باستخدام console.log.",
            "تستخدم console.log() لعرض المعلومات في وحدة التحكم أثناء تشغيل JavaScript.",
            """console.log("Hello");
console.log(5 + 5);""",
            "ما وظيفة console.log؟",
            listOf("عرض النتائج", "إغلاق الصفحة", "حذف المتغيرات", "إنشاء صورة"),
            0,
            "console.log تعرض القيم والرسائل في وحدة التحكم."
        ),

        Lesson(
            "cpp1", "C++", "المتغيرات",
            "تعرّف على أنواع البيانات.",
            "في C++ نحدد نوع المتغير مثل int للأعداد الصحيحة وstring للنصوص.",
            """int age = 15;
string name = "Ali";""",
            "أي نوع يستخدم للأعداد الصحيحة؟",
            listOf("string", "int", "text", "word"),
            1,
            "int هو النوع الأساسي لتخزين الأعداد الصحيحة."
        ),

        Lesson(
            "cpp2", "C++", "الطباعة",
            "عرض النصوص باستخدام cout.",
            "نستخدم cout مع العامل << لعرض النصوص والقيم على الشاشة.",
            """#include <iostream>
using namespace std;

int main() {
    cout << "Hello";
    return 0;
}""",
            "ما الأداة المستخدمة للطباعة؟",
            listOf("cin", "cout", "print", "input"),
            1,
            "cout تستخدم لإخراج النصوص والقيم."
        )
    )

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = bg
        window.navigationBarColor = bg

        showHome()
    }

    private fun makeBackground(color: Int): GradientDrawable {
        return GradientDrawable().apply {
            setColor(color)
            cornerRadius = dp(15).toFloat()
            setStroke(dp(1), Color.rgb(48, 40, 65))
        }
    }

    private fun label(
        value: String,
        size: Float = 15f,
        color: Int = white,
        bold: Boolean = false
    ): TextView {
        return TextView(this).apply {
            text = value
            textSize = size
            setTextColor(color)
            gravity = Gravity.CENTER_VERTICAL
            if (bold) typeface = Typeface.DEFAULT_BOLD
        }
    }

    private fun makeButton(
        value: String,
        action: () -> Unit
    ): TextView {
        return TextView(this).apply {
            text = value
            textSize = 14f
            setTextColor(white)
            gravity = Gravity.CENTER
            background = makeBackground(surface)
            setPadding(dp(12), dp(13), dp(12), dp(13))
            setOnClickListener { action() }
        }
    }

    private fun startPage(title: String) {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutDirection = View.LAYOUT_DIRECTION_RTL
            setBackgroundColor(bg)
        }

        val header = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(dp(18), dp(15), dp(18), dp(15))
            setBackgroundColor(surface)
        }

        val brand = label("DEVORA", 21f, purple, true)
        header.addView(brand, LinearLayout.LayoutParams(0, -2, 1f))

        val menu = label("☰", 24f, white, true)
        menu.gravity = Gravity.CENTER
        menu.setPadding(dp(12), 0, dp(12), 0)
        menu.setOnClickListener { showMenu() }
        header.addView(menu)

        root.addView(header)

        val heading = label(title, 22f, white, true)
        heading.setPadding(dp(18), dp(18), dp(18), dp(12))
        root.addView(heading)

        val scroll = ScrollView(this)

        content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(15), dp(8), dp(15), dp(25))
        }

        scroll.addView(content)

        root.addView(
            scroll,
            LinearLayout.LayoutParams(-1, 0, 1f)
        )

        val bottom = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            setPadding(dp(5), dp(8), dp(5), dp(8))
            setBackgroundColor(surface)
        }

        val destinations = listOf(
            "الرئيسية",
            "الأكاديمية",
            "تقدمي",
            "التحدي"
        )

        destinations.forEach { destination ->
            val item = label(destination, 12f, muted)
            item.gravity = Gravity.CENTER
            item.setPadding(dp(5), dp(12), dp(5), dp(12))
            item.setOnClickListener {
                when (destination) {
                    "الرئيسية" -> showHome()
                    "الأكاديمية" -> showAcademy()
                    "تقدمي" -> showProgress()
                    "التحدي" -> showDailyChallenge()
                }
            }

            bottom.addView(
                item,
                LinearLayout.LayoutParams(0, -2, 1f)
            )
        }

        root.addView(bottom)
        setContentView(root)
    }

    private fun addCard(
        title: String,
        description: String,
        action: (() -> Unit)? = null
    ) {
        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            background = makeBackground(surface)
            setPadding(dp(16), dp(15), dp(16), dp(15))
        }

        card.addView(label(title, 17f, purple, true))

        val desc = label(description, 13f, muted)
        desc.setPadding(0, dp(8), 0, dp(5))
        card.addView(desc)

        if (action != null) {
            val open = label("فتح القسم ←", 13f, white, true)
            open.setPadding(0, dp(9), 0, 0)
            card.addView(open)
            card.setOnClickListener { action() }
        }

        val params = LinearLayout.LayoutParams(-1, -2)
        params.bottomMargin = dp(12)
        content.addView(card, params)
    }

    private fun addActionButton(
        title: String,
        action: () -> Unit
    ) {
        val btn = makeButton(title, action)
        val params = LinearLayout.LayoutParams(-1, dp(50))
        params.bottomMargin = dp(10)
        content.addView(btn, params)
    }

    private fun completedCount(): Int {
        return lessons.count {
            prefs.getBoolean("done_${it.id}", false)
        }
    }

    private fun showHome() {
        startPage("الرئيسية")

        val done = completedCount()
        val xp = prefs.getInt("xp", 0)

        addCard(
            "مرحباً بك في DEVORA",
            "منصة تعليم البرمجة من الصفر إلى مستويات متقدمة."
        )

        addCard(
            "مستواك التعليمي",
            "الدروس المكتملة: $done / ${lessons.size}\nالنقاط المكتسبة: $xp XP"
        )

        addCard(
            "أكاديمية البرمجة",
            "اختر لغة برمجة وابدأ التعلم.",
            ::showAcademy
        )

        addCard(
            "اختبار سريع",
            "اختبر معلوماتك البرمجية.",
            ::showQuickQuiz
        )

        addCard(
            "التحدي اليومي",
            "سؤال تعليمي جديد لتطوير مهاراتك.",
            ::showDailyChallenge
        )

        addCard(
            "إنجازاتي",
            "شاهد تقدمك والدروس التي أكملتها.",
            ::showProgress
        )
    }

    private fun showAcademy() {
        startPage("أكاديمية البرمجة")

        addCard(
            "ابدأ من هنا",
            "اختر لغة البرمجة التي تريد تعلمها. كل لغة تحتوي على دروس واختبارات."
        )

        languages.forEach { language ->
            val count = lessons.count { it.language == language }
            val done = lessons.count {
                it.language == language &&
                    prefs.getBoolean("done_${it.id}", false)
            }

            addCard(
                language,
                "$done من $count دروس مكتملة",
                { showLessons(language) }
            )
        }
    }

    private fun showLessons(language: String) {
        startPage("دروس $language")

        val selected = lessons.filter { it.language == language }

        selected.forEachIndexed { index, lesson ->
            val done = prefs.getBoolean("done_${lesson.id}", false)

            addCard(
                "${index + 1}. ${lesson.title}",
                lesson.description + if (done) {
                    "\n✓ مكتمل"
                } else {
                    "\nغير مكتمل"
                },
                { showLesson(lesson) }
            )
        }
    }

    private fun showLesson(lesson: Lesson) {
        startPage(lesson.title)

        addCard(
            "شرح الدرس",
            lesson.explanation
        )

        addCard(
            "مثال برمجي",
            lesson.example
        )

        addActionButton("ابدأ اختبار الدرس") {
            showLessonQuiz(lesson)
        }

        val completed = prefs.getBoolean("done_${lesson.id}", false)

        if (completed) {
            addCard(
                "حالة الدرس",
                "هذا الدرس مكتمل بالفعل."
            )
        } else {
            addActionButton("تحديد الدرس كمكتمل") {
                prefs.edit()
                    .putBoolean("done_${lesson.id}", true)
                    .putInt(
                        "xp",
                        prefs.getInt("xp", 0) + 10
                    )
                    .apply()

                Toast.makeText(
                    this,
                    "تم إكمال الدرس! +10 XP",
                    Toast.LENGTH_SHORT
                ).show()

                showLesson(lesson)
            }
        }

        addActionButton("العودة إلى الدروس") {
            showLessons(lesson.language)
        }
    }

    private fun showLessonQuiz(lesson: Lesson) {
        startPage("اختبار ${lesson.language}")

        addCard(
            "السؤال",
            lesson.question
        )

        val group = RadioGroup(this).apply {
            orientation = RadioGroup.VERTICAL
            background = makeBackground(surface)
            setPadding(dp(12), dp(10), dp(12), dp(10))
        }

        val choices = mutableListOf<RadioButton>()

        lesson.options.forEachIndexed { index, option ->
            val radio = RadioButton(this).apply {
                text = option
                textSize = 14f
                setTextColor(white)
                buttonTintList = android.content.res.ColorStateList.valueOf(purple)
                id = index + 100
            }

            choices.add(radio)
            group.addView(radio)
        }

        content.addView(group)

        addActionButton("تحقق من الإجابة") {
            val selected = group.checkedRadioButtonId

            if (selected == -1) {
                Toast.makeText(
                    this,
                    "اختر إجابة أولاً",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val index = selected - 100
                val correct = index == lesson.answer

                AlertDialog.Builder(this)
                    .setTitle(
                        if (correct) "إجابة صحيحة" else "إجابة غير صحيحة"
                    )
                    .setMessage(
                        (if (correct) {
                            "أحسنت! إجابتك صحيحة.\n\n"
                        } else {
                            "الإجابة الصحيحة: ${lesson.options[lesson.answer]}\n\n"
                        }) + lesson.reason
                    )
                    .setPositiveButton("متابعة") { _, _ ->
                        if (correct) {
                            val key = "quiz_${lesson.id}"

                            if (!prefs.getBoolean(key, false)) {
                                prefs.edit()
                                    .putBoolean(key, true)
                                    .putInt(
                                        "xp",
                                        prefs.getInt("xp", 0) + 5
                                    )
                                    .apply()
                            }
                        }

                        showLesson(lesson)
                    }
                    .show()
            }
        }

        addActionButton("العودة للدرس") {
            showLesson(lesson)
        }
    }

    private fun showQuickQuiz() {
        val lesson = lessons.random()
        showLessonQuiz(lesson)
    }

    private fun showDailyChallenge() {
        startPage("التحدي اليومي")

        val challenge = lessons[(completedCount()) % lessons.size]

        addCard(
            "تحدي اليوم",
            "اللغة: ${challenge.language}\n\n${challenge.question}"
        )

        addActionButton("ابدأ التحدي") {
            showLessonQuiz(challenge)
        }

        addCard(
            "نصيحة",
            "اقرأ الدرس المرتبط بالسؤال قبل محاولة الإجابة."
        )
    }

    private fun showProgress() {
        startPage("تقدمي التعليمي")

        val done = completedCount()
        val total = lessons.size
        val percent = if (total == 0) 0 else done * 100 / total
        val xp = prefs.getInt("xp", 0)

        addCard(
            "نسبة الإنجاز",
            "$percent% مكتمل\n$done من $total دروس"
        )

        val progress = ProgressBar(
            this,
            null,
            android.R.attr.progressBarStyleHorizontal
        ).apply {
            max = 100
            this.progress = percent
            progressTintList =
                android.content.res.ColorStateList.valueOf(purple)
        }

        val progressParams = LinearLayout.LayoutParams(-1, dp(12))
        progressParams.bottomMargin = dp(20)
        content.addView(progress, progressParams)

        addCard(
            "النقاط",
            "$xp XP"
        )

        addCard(
            "الدروس المكتملة",
            lessons.filter {
                prefs.getBoolean("done_${it.id}", false)
            }.joinToString("\n") {
                "✓ ${it.language}: ${it.title}"
            }.ifEmpty {
                "لم تكمل أي درس بعد. ابدأ أول درس!"
            }
        )

        addActionButton("العودة للأكاديمية") {
            showAcademy()
        }
    }

    private fun showMenu() {
        val items = arrayOf(
            "الرئيسية",
            "أكاديمية البرمجة",
            "التقدم والإنجازات",
            "التحدي اليومي",
            "حول التطبيق"
        )

        AlertDialog.Builder(this)
            .setTitle("DEVORA")
            .setItems(items) { _, index ->
                when (index) {
                    0 -> showHome()
                    1 -> showAcademy()
                    2 -> showProgress()
                    3 -> showDailyChallenge()
                    4 -> showAbout()
                }
            }
            .setNegativeButton("إغلاق", null)
            .show()
    }

    private fun showAbout() {
        startPage("حول DEVORA")

        addCard(
            "DEVORA 1.1",
            "منصة تعليمية لتعلم البرمجة.\n\n" +
                "اللغات: Python و Kotlin و Java و JavaScript و C++.\n\n" +
                "يتم حفظ تقدمك محلياً على جهازك."
        )
    }
}
