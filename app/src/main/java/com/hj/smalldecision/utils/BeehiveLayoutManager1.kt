package com.hj.smalldecision.utils

import android.content.Context
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import java.util.*

class BeehiveLayoutManager1: RecyclerView.LayoutManager {
    var FIRSTGROUP_MARGIN_SECONDGROUP = 50 //第一小组与第二小组间距
    var GROUP_PADDING = 120 //组距
    private var mContext: Context? = null
    private var mColumnSize: Int? = null
    private var mHorizontalOffset = 0
    private var mVerticalOffset = 0
    private var mTotalWidth = 0
    private var mTotalHeight = 0
    private var mItemFrames: Pool<Rect>? = null

    constructor(context: Context) : this(context, DEFAULT_GROUP_SIZE) {
    }

    constructor(context: Context,columnSize: Int): super(){
        mContext = context
        mColumnSize = columnSize
        mItemFrames = Pool(object: Pool.New<Rect> {
            override fun get(): Rect {
                return Rect()
            }
        })
    }

    fun setGroupPadding(padding: Int) {
        GROUP_PADDING = padding
    }

    fun setFristMarginSecondGroup(margin: Int) {
        FIRSTGROUP_MARGIN_SECONDGROUP = margin
    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onLayoutChildren(recycler: Recycler, state: RecyclerView.State) {
        if (itemCount <= 0 || state.isPreLayout) {
            return
        }
        detachAndScrapAttachedViews(recycler)
        val rvwidth = recyclerViewWidth //RecyclerView width
        val itemWidth = rvwidth / mColumnSize!! //正方形宽度
        val itemHeight = itemWidth
        val firstgroupnum = mColumnSize!! / 2 //第一小组最多显示个数
        val secondgroupnum =
            if (mColumnSize!! % 2 == 0) mColumnSize!! / 2 else mColumnSize!! / 2 + 1 //第二组最多显示个数
        val r = (itemWidth / 2).toFloat() //内切圆半径
        val fleft = (rvwidth - (itemWidth * firstgroupnum + r * (firstgroupnum - 1))) / 2 //第一组开始偏移量
        val firstgroupitemleftposition = 0f //第一组item左边位置
        val sright = fleft - itemWidth * 3 / 4 //第二组开始偏移量
        val d = (itemHeight / 4 * (2 - Math.sqrt(3.0))).toFloat() //六边形到边到内切圆的距离
        val secondgroupmarginfirstgroup = itemHeight.toFloat() / 2 - d
        val topmargin = 50f
        var toppositoion = 0f
        val groupDatas = GetGroupData(firstgroupnum, secondgroupnum)
        for (index in groupDatas.indices) {
            toppositoion = index * itemHeight + topmargin + GROUP_PADDING * index
            toppositoion = toppositoion - d * 2 * index
            val g = groupDatas[index]
            for (firstgroupindex in g.FirstGroup.indices) {
                val left = (fleft + firstgroupindex * (itemWidth + r)).toInt()
                val top = toppositoion.toInt()
                val right = left + itemWidth
                val bottom = top + itemHeight
                val rect = g.FirstGroup[firstgroupindex].rect
                rect!![left, top, right] = bottom
            }
            toppositoion += secondgroupmarginfirstgroup + FIRSTGROUP_MARGIN_SECONDGROUP
            for (secondgroupindex in g.SecondGroup.indices) {
                val left = (sright + secondgroupindex * (itemWidth + r)).toInt()
                val top = toppositoion.toInt()
                val right = left + itemWidth
                val bottom = top + itemHeight
                val rect = g.SecondGroup[secondgroupindex].rect
                rect!![left, top, right] = bottom
            }
        }
        mTotalWidth = Math.max(firstgroupnum * itemWidth, horizontalSpace)
        var totalHeight = mColumnSize!! * itemHeight
        if (!isItemInFirstLine(itemCount - 1)) {
            totalHeight += itemHeight / 2
        }
        mTotalHeight = Math.max(totalHeight, verticalSpace)
        fill(recycler, state)
    }

    /** 将数据转化为组数据
     * @param firstgroupnum 第一小组最多显示个数
     * @param secondgroupnum 第二小组最多显示个数
     * @return
     */
    private fun GetGroupData(firstgroupnum: Int, secondgroupnum: Int): List<GroupData> {
        var groupnums = itemCount / (firstgroupnum + secondgroupnum)
        if (itemCount % (firstgroupnum + secondgroupnum) != 0) {
            groupnums++
        }
        val groupdata: MutableList<GroupData> = ArrayList()
        var ItemIndex = 0
        for (index in 0 until groupnums) {
            val g = GroupData()
            for (i in 0 until firstgroupnum) {
                val item = GroupItem()
                item.itemindex = ItemIndex++
                item.rect = mItemFrames!![item.itemindex]
                g.FirstGroup.add(item)
            }
            for (i in 0 until secondgroupnum) {
                val item = GroupItem()
                item.itemindex = ItemIndex++
                item.rect = mItemFrames!![item.itemindex]
                g.SecondGroup.add(item)
            }
            groupdata.add(g)
        }
        return groupdata
    }

    val itemWidth: Int
        get() = recyclerViewWidth / mColumnSize!!
    private val recyclerViewWidth: Int
        private get() {
            var width = width
            if (width <= 0) {
                val m = mContext!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                val met = DisplayMetrics()
                m.defaultDisplay.getRealMetrics(met)
                width = met.widthPixels
            }
            return width
        }

    private fun fill(recycler: Recycler, state: RecyclerView.State) {
        if (itemCount <= 0 || state.isPreLayout) {
            return
        }
        val displayRect = Rect(
            mHorizontalOffset, mVerticalOffset,
            horizontalSpace + mHorizontalOffset,
            verticalSpace + mVerticalOffset
        )
        for (i in 0 until itemCount) {
            val frame = mItemFrames!![i]
            if (Rect.intersects(displayRect, frame!!)) {
                val scrap = recycler.getViewForPosition(i)
                addView(scrap)
                measureChildWithMargins(scrap, 0, 0)
                layoutDecorated(
                    scrap, frame.left - mHorizontalOffset, frame.top - mVerticalOffset,
                    frame.right - mHorizontalOffset, frame.bottom - mVerticalOffset
                )
            }
        }
    }

    override fun scrollVerticallyBy(dy: Int, recycler: Recycler, state: RecyclerView.State): Int {
        var dy = dy
        detachAndScrapAttachedViews(recycler)
        if (mVerticalOffset + dy < 0) {
            dy = -mVerticalOffset
        } else if (mVerticalOffset + dy > mTotalHeight - verticalSpace) {
            dy = mTotalHeight - verticalSpace - mVerticalOffset
        }
        offsetChildrenVertical(-dy)
        fill(recycler, state)
        mVerticalOffset += dy
        return dy
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: Recycler, state: RecyclerView.State): Int {
        var dx = dx
        detachAndScrapAttachedViews(recycler)
        if (mHorizontalOffset + dx < 0) {
            dx = -mHorizontalOffset
        } else if (mHorizontalOffset + dx > mTotalWidth - horizontalSpace) {
            dx = mTotalWidth - horizontalSpace - mHorizontalOffset
        }
        offsetChildrenHorizontal(-dx)
        fill(recycler, state)
        mHorizontalOffset += dx
        return dx
    }

    override fun canScrollVertically(): Boolean {
        return true
    }

    override fun canScrollHorizontally(): Boolean {
        return false
    }

    private fun isItemInFirstLine(index: Int): Boolean {
        val firstLineSize = mColumnSize!! / 2
        return index < firstLineSize || index >= mColumnSize!! && index % mColumnSize!! < firstLineSize
    }

    private val groupSize: Int
        private get() = Math.ceil((itemCount / mColumnSize!!.toFloat()).toDouble()).toInt()
    private val horizontalSpace: Int
        private get() = width - paddingLeft - paddingRight
    private val verticalSpace: Int
        private get() = height - paddingTop - paddingBottom

    private inner class GroupData {
        var itemIndex = 0
        var FirstGroup: MutableList<GroupItem> = ArrayList()
        var SecondGroup: MutableList<GroupItem> = ArrayList()
    }

    private inner class GroupItem {
        var rect: Rect? = null
        var itemindex = 0
    }

    companion object {
        const val DEFAULT_GROUP_SIZE = 5
    }

}