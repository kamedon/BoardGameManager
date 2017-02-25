package com.kamedon.boardgamemanager.presentation.view

import android.support.v7.widget.RecyclerView
import java.util.*

/**
 * Created by kamei.hidetoshi on 2016/09/24.
 */

abstract class RecyclerArrayAdapter<T, VH : RecyclerView.ViewHolder>(private val mData: MutableList<T> = ArrayList<T>()) : RecyclerView.Adapter<VH>() {
    val data: List<T>
        get() = mData
    private val lock = Any()

    fun add(item: T) {
        val position = mData.size
        synchronized(lock) {
            mData.add(item)
        }
        notifyItemInserted(position)
    }

    fun addAll(collection: Collection<T>) {
        val position = mData.size
        val count = collection.size
        synchronized(lock) {
            mData.addAll(collection)
        }
        notifyItemRangeChanged(position, count)
    }

    fun insert(position: Int, item: T) {
        synchronized(lock) {
            mData.add(position, item)
        }
        notifyItemInserted(position)
    }

    fun remove(item: T) {
        val position = mData.indexOf(item)
        synchronized(lock) {
            mData.remove(item)
        }
        notifyItemRemoved(position)
    }

    fun remove(position: Int) {
        synchronized(lock) {
            mData.removeAt(position)
        }
        notifyItemRemoved(position)
    }

    fun clear() {
        val size = mData.size
        synchronized(lock) {
            mData.clear()
        }
        notifyItemRangeRemoved(0, size)
    }

    override fun getItemCount(): Int {
        return mData.size
    }


    fun move(from: Int, to: Int) {
        synchronized(lock) {
            val fT = mData.removeAt(from)
            if (from < to) {
                mData.add(to - 1, fT)
            } else {
                mData.add(to, fT)
            }
        }
        notifyItemMoved(from, to)
    }
}
