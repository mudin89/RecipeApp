package mudin.iv.recipeapp.view.custom

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatTextView
import mudin.iv.recipeapp.R
import mudin.iv.recipeapp.utils.AppConstants
import mudin.iv.recipeapp.utils.AppFont


class CustomTextView : AppCompatTextView {
    /**
     * The Context.
     */
    private var mContext: Context? = null

    /**
     * The constructor
     *
     * @param context the current context
     */
    constructor(context: Context?) : super(context!!) {
        initialize(context, null, 0)
    }

    /**
     * The constructor
     *
     * @param context the current context
     * @param attrs   the attributes of the view
     */
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        initialize(context, attrs, 0)
    }

    /**
     * The constructor
     *
     * @param context      the current context
     * @param attrs        the attributes
     * @param defStyleAttr the styles
     */
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context!!, attrs, defStyleAttr) {
        initialize(context, attrs, defStyleAttr)
    }

    /**
     * To initialize the class parameters
     *
     * @param context  the context
     * @param attrs    the attributes
     * @param defStyle the styles
     */
    fun initialize(context: Context?, attrs: AttributeSet?, defStyle: Int) {
        this.mContext = context
        setCustomTypeFace(attrs)
    }

    /**
     * This function sets the custom type face
     *
     * @param attrs the attributes
     */
    private fun setCustomTypeFace(attrs: AttributeSet?) {
        try {
            if (attrs != null) {
                val typedArray = mContext!!.obtainStyledAttributes(attrs,
                        R.styleable.CustomTextView)
                val fontValue = typedArray.getInt(R.styleable.CustomTextView_fontName, 0)
                val diagnalTypeface = Typeface.createFromAsset(mContext!!
                        .getAssets(), AppConstants.FONT.FONT_FOLDER_NAME + AppFont.getFontNameFromIndex(fontValue))
                this.setTypeface(diagnalTypeface, Typeface.NORMAL)
                typedArray.recycle()
            }
        } catch (ex: Exception) {
            Log.d("Exception ", "CustomTextView SetCustomTypeFace $ex")
        }
    }

    /**
     * This will set the custom font of this text view. This method is for setting it programmatically
     *
     * @param fontValue the enum value DiagnalFont
     */
    fun setFont(fontValue: AppFont) {
        this.setTypeface(getTypeFace(context, fontValue), Typeface.NORMAL)
    }

    /**
     * Sets the background of the view
     *
     * @param backgroundDrawable the background drawable
     */
    override fun setBackground(backgroundDrawable: Drawable) {
//        val layer = DiagnalBackgroundDrawable(
//                backgroundDrawable)
        super.setBackground(backgroundDrawable)
    }

    /**
     * Sets the background of the view
     *
     * @param backgroundDrawable the background drawable
     */
    override fun setBackgroundDrawable(background: Drawable?) {
//        val layer = DiagnalBackgroundDrawable(
//                backgroundDrawable)
        super.setBackgroundDrawable(background)
    } //    public void setFormattedText(String message) {

    //        Spannable formattedMessage = TextFontHelper.formatText(getContext(), message);
    //        setText(formattedMessage);
    //    }
    companion object {
        fun getTypeFace(context: Context?, fontValue: AppFont): Typeface {
            return Typeface.createFromAsset(context!!
                    .getAssets(), AppConstants.FONT.FONT_FOLDER_NAME + fontValue.toString() + AppConstants.FONT.FONT_EXTENSION)
        }
    }
}