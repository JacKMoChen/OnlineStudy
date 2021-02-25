package com.jack.mine.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import androidx.annotation.Keep
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.ObservableField
import com.jack.mine.R
import com.jack.mine.databinding.ViewItemSettingBinding

/**
 * @description: 设置item组合控件
 * @author:  JacKMoChen
 * @email:   chenjifangvip@163.com
 * @time :   2021/1/29 10:46
 */
class ItemSettingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var itemBean = ItemSettingBean()
    private val obItemInfo = ObservableField<ItemSettingBean>(itemBean)

    init {
        //关联布局
        ViewItemSettingBinding.inflate(LayoutInflater.from(context), this, true).apply {
            info = obItemInfo
        }
        setBackgroundColor(Color.WHITE)

        //region 读取属性配置
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.ItemSettingView).apply {
            itemBean.iconRes =
                getResourceId(R.styleable.ItemSettingView_icon, R.drawable.ic_launcher)
            val iconColor = getColor(R.styleable.ItemSettingView_iconColor, 0)
            itemBean.iconColor = iconColor

            itemBean.title = getString(R.styleable.ItemSettingView_title) ?: ""
            val tileColor = getColor(
                R.styleable.ItemSettingView_titleColor,
                resources.getColor(R.color.colorPrimaryText)
            )
            itemBean.titleColor = tileColor

            itemBean.desc = getString(R.styleable.ItemSettingView_desc) ?: ""
            val descColor = getColor(
                R.styleable.ItemSettingView_descColor,
                resources.getColor(R.color.colorSecondaryText)
            )
            itemBean.descColor = descColor

            itemBean.arrowRes =
                getResourceId(R.styleable.ItemSettingView_arrow, R.drawable.iv_setting_arrow)
            val arrowColor = getColor(R.styleable.ItemSettingView_arrowColor, 0)
            itemBean.arrowColor = arrowColor
        }
        attributes.recycle()
        //endregion

    }

    //region 设置资源
    /**
     * 设置整个item对象
     */
    fun setInfo(info: ItemSettingBean) {
        itemBean = info
        obItemInfo.set(itemBean)
    }

    /**
     * 设置title
     */
    fun setTitle(title: String) {
        itemBean.title = title
    }

    /**
     * 设置描述
     */
    fun setDesc(desc: String) {
        itemBean.desc = desc
    }

    /**
     * 设置图标
     */
    fun setIcon(iconRes: Any) {
        itemBean.iconRes = iconRes
    }

    fun setArrow(arrowRes: Any) {
        itemBean.arrowRes = arrowRes
    }
    //endregion

    //region 设置颜色
    fun setTitleColor(color: Int) {
        itemBean.titleColor = color
    }

    fun setDescColor(color: Int) {
        itemBean.descColor = color
    }

    fun setIconColor(color: Int) {
        itemBean.iconColor = color
    }

    fun setArrowColor(color: Int) {
        itemBean.arrowColor = color
    }
    //endregion

    //region 设置点击事件
    fun setArrowListener(clickListener: OnClickListener) {
        itemBean.arrowListener = clickListener
    }

    //endregion

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return hasOnClickListeners()
    }
}

@Keep
data class ItemSettingBean(
    var iconRes: Any = R.drawable.ic_launcher,
    var title: String = "",
    var desc: String = "",
    var titleColor: Int = R.color.colorPrimaryText,
    var descColor: Int = R.color.colorSecondaryText,
    var iconColor: Int = 0,
    var arrowColor: Int = R.color.colorSecondaryText,
    var arrowRes: Any = R.drawable.iv_setting_arrow
) {
    var arrowListener: View.OnClickListener? = null
}