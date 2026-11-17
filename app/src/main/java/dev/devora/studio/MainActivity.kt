
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
    private val surface = Color.rgb(21, 20, 31)
    private val purple = Color.rgb(151, 71, 255)
    private val textColor = Color.WHITE
    private val muted = Color.rgb(165, 160, 180)

    private lateinit var content: LinearLayout
    private lateinit var pageTitle: TextView

    private val sections = listOf(
        "الرئيسية",
        "تعلّم البرمجة",
        "محرر الأكواد",
        "مختبر البرمجة",
        "التحديات البرمجية",
        "الاختبارات",
        "مشاريعي",
        "الإنجازات",
        "الإعدادات",
        "حول التطبيق"
    )

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = bg
        window.navigationBarColor = bg

        showPage("الرئيسية")
    }

    private fun background(color: Int, radius: Int = 16): GradientDrawable {
        return GradientDrawable().apply {
            setColor(color)
            cornerRadius = dp(radius).toFloat()
            setStroke(dp(1), Color.rgb(48, 42, 65))
        }
    }

    private fun text(
        value: String,
        size: Float = 15f,
        color: Int = textColor,
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

    private fun button(
        title: String,
        action: () -> Unit
    ): TextView {
        return TextView(this).apply {
            text = title
            textSize = 15f
            setTextColor(textColor)
            gravity = Gravity.CENTER
            background = background(surface, 13)
            setPadding(dp(14), dp(14), dp(14), dp(14))
            setOnClickListener { action() }
        }
    }

    private fun showPage(page: String) {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutDirection = View.LAYOUT_DIRECTION_RTL
            setBackgroundColor(bg)
        }

        val header = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(dp(18), dp(16), dp(18), dp(16))
            setBackgroundColor(surface)
        }

        val brand = text("DEVORA", 21f, purple, true)
        header.addView(brand, LinearLayout.LayoutParams(0, -2, 1f))

        val menu = TextView(this).apply {
            text = "☰"
            textSize = 25f
            setTextColor(textColor)
            gravity = Gravity.CENTER
            setPadding(dp(12), 0, dp(12), 0)
            setOnClickListener { showMenu() }
        }
        header.addView(menu)

        root.addView(header)

        pageTitle = text(page, 22f, textColor, true)
        pageTitle.setPadding(dp(20), dp(20), dp(20), dp(12))
        root.addView(pageTitle)

        val scroll = ScrollView(this)
        content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(16), dp(8), dp(16), dp(24))
        }

        scroll.addView(content)
        root.addView(
            scroll,
            LinearLayout.LayoutParams(-1, 0, 1f)
        )

        val navigation = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            setPadding(dp(8), dp(8), dp(8), dp(8))
            setBackgroundColor(surface)
        }

        listOf("الرئيسية", "التعلّم", "المحرر", "مشاريعي").forEach { item ->
            val navButton = TextView(this).apply {
                text = item
                textSize = 12f
                setTextColor(if (
                    page == item ||
                    (page == "تعلّم البرمجة" && item == "التعلّم") ||
                    (page == "محرر الأكواد" && item == "المحرر")
                ) purple else muted)
                gravity = Gravity.CENTER
                setPadding(dp(8), dp(12), dp(8), dp(12))
                setOnClickListener {
                    val destination = when (item) {
                        "التعلّم" -> "تعلّم البرمجة"
                        "المحرر" -> "محرر الأكواد"
                        else -> item
                    }
                    showPage(destination)
                }
            }
            navigation.addView(
                navButton,
                LinearLayout.LayoutParams(0, -2, 1f)
            )
        }

        root.addView(navigation)
        setContentView(root)

        when (page) {
            "الرئيسية" -> homePage()
            "تعلّم البرمجة" -> learningPage()
            "محرر الأكواد" -> editorPage()
            "مختبر البرمجة" -> labPage()
            "التحديات البرمجية" -> challengesPage()
            "الاختبارات" -> quizzesPage()
            "مشاريعي" -> projectsPage()
            "الإنجازات" -> achievementsPage()
            "الإعدادات" -> settingsPage()
            "حول التطبيق" -> aboutPage()
            else -> homePage()
        }
    }

    private fun addCard(
        title: String,
        description: String,
        action: (() -> Unit)? = null
    ) {
        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            background = background(surface)
            setPadding(dp(17), dp(16), dp(17), dp(16))
        }

        card.addView(text(title, 17f, purple, true))

        val desc = text(description, 13f, muted)
        desc.setPadding(0, dp(8), 0, dp(4))
        card.addView(desc)

        if (action != null) {
            val open = text("فتح القسم ←", 13f, textColor, true)
            open.gravity = Gravity.CENTER_VERTICAL or Gravity.RIGHT
            open.setPadding(0, dp(10), 0, 0)
            card.addView(open)
            card.setOnClickListener { action() }
        }

        val params = LinearLayout.LayoutParams(-1, -2)
        params.bottomMargin = dp(12)
        content.addView(card, params)
    }

    private fun homePage() {
        addCard(
            "مرحباً بك في DEVORA",
            "بيئة تعليم وتطوير برمجي تساعدك على تعلم البرمجة وإنشاء مشاريعك."
        )

        addCard("تعلّم البرمجة", "ابدأ رحلتك مع لغات البرمجة والدروس.", {
            showPage("تعلّم البرمجة")
        })

        addCard("محرر الأكواد", "اكتب الأكواد ونظّم أفكارك البرمجية.", {
            showPage("محرر الأكواد")
        })

        addCard("مختبر البرمجة", "مساحة للتجارب والتطبيقات التعليمية.", {
            showPage("مختبر البرمجة")
        })

        addCard("التحديات البرمجية", "اختبر مهاراتك من خلال تحديات متنوعة.", {
            showPage("التحديات البرمجية")
        })

        addCard("مشاريعي", "تابع مشاريعك البرمجية.", {
            showPage("مشاريعي")
        })
    }

    private fun learningPage() {
        addCard("Python", "تعلّم أساسيات لغة Python.")
        addCard("Kotlin", "تعلّم تطوير تطبيقات Android.")
        addCard("Java", "تعلّم أساسيات Java.")
        addCard("JavaScript", "تعلّم برمجة الويب.")
        addCard("C++", "تعلّم البرمجة بلغة C++.")
    }

    private fun editorPage() {
        content.addView(text("محرر الأكواد", 19f, purple, true))

        val language = Spinner(this)
        val languages = arrayOf(
            "Python",
            "Kotlin",
            "Java",
            "JavaScript",
            "C++"
        )

        language.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            languages
        )
        content.addView(language)

        val code = EditText(this).apply {
            hint = "// اكتب الكود هنا"
            setHintTextColor(muted)
            setTextColor(Color.WHITE)
            textSize = 14f
            typeface = Typeface.MONOSPACE
            gravity = Gravity.TOP or Gravity.LEFT
            minLines = 12
            setPadding(dp(14), dp(14), dp(14), dp(14))
            background = background(Color.rgb(15, 14, 23), 12)
            setSingleLine(false)
        }

        val codeParams = LinearLayout.LayoutParams(-1, dp(280))
        codeParams.topMargin = dp(12)
        content.addView(code, codeParams)

        val save = button("حفظ النص") {
            val value = code.text.toString()
            if (value.isBlank()) {
                Toast.makeText(
                    this,
                    "اكتب بعض الأكواد أولاً",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                getPreferences(MODE_PRIVATE)
                    .edit()
                    .putString("devora_saved_code", value)
                    .apply()

                Toast.makeText(
                    this,
                    "تم حفظ النص محلياً",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        val saveParams = LinearLayout.LayoutParams(-1, dp(50))
        saveParams.topMargin = dp(12)
        content.addView(save, saveParams)

        val runInfo = text(
            "تشغيل الأكواد غير مفعّل في هذا الإصدار. سيتم دمج محركات التشغيل لاحقاً.",
            13f,
            muted
        )
        runInfo.setPadding(0, dp(15), 0, 0)
        content.addView(runInfo)
    }

    private fun labPage() {
        addCard(
            "مختبر البرمجة",
            "مساحة تعليمية لتجربة المفاهيم البرمجية. تشغيل الأكواد يحتاج محركاً مخصصاً."
        )
        addCard("تجربة المتغيرات", "تعلّم كيفية تخزين البيانات.")
        addCard("التعامل مع الشروط", "تعرّف على if و else.")
        addCard("الحلقات التكرارية", "تعلّم for و while.")
    }

    private fun challengesPage() {
        addCard("التحدي الأول", "اكتب برنامجاً يعرض رسالة ترحيب.")
        addCard("التحدي الثاني", "تعرّف على أنواع المتغيرات.")
        addCard("التحدي الثالث", "افهم الشروط البرمجية.")
    }

    private fun quizzesPage() {
        addCard("اختبار أساسيات البرمجة", "اختبر فهمك للمفاهيم الأساسية.")
        addCard("اختبار Python", "أسئلة تعليمية حول Python.")
        addCard("اختبار Kotlin", "أسئلة تعليمية حول Kotlin.")
    }

    private fun projectsPage() {
        addCard(
            "مشاريعي",
            "ستتم إضافة إنشاء المشاريع وإدارة الملفات في إصدار قادم."
        )
        addCard("مشروع جديد", "إدارة المشاريع البرمجية قيد التطوير.")
    }

    private fun achievementsPage() {
        addCard("الإنجازات", "ستظهر إنجازاتك هنا عند إضافة نظام التقدم.")
        addCard("مستوى التعلم", "لم يتم احتساب مستوى التعلم بعد.")
    }

    private fun settingsPage() {
        addCard("المظهر", "واجهة DEVORA الحالية: أسود وبنفسجي.")
        addCard("اللغة", "اللغة الحالية: العربية.")
        addCard("الإصدار", "DEVORA 1.0")
    }

    private fun aboutPage() {
        addCard(
            "DEVORA",
            "تطبيق تعليمي لتعلم البرمجة وكتابة الأكواد وتطوير المشاريع."
        )
        addCard("الإصدار", "1.0")
        addCard("التقنيات", "Android Native • Kotlin • Java 17")
    }

    private fun showMenu() {
        AlertDialog.Builder(this)
            .setTitle("DEVORA")
            .setItems(sections.toTypedArray()) { _, index ->
                showPage(sections[index])
            }
            .setNegativeButton("إغلاق", null)
            .show()
    }
}
