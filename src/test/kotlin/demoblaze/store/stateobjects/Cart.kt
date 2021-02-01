package demoblaze.store.stateobjects

import com.google.common.util.concurrent.AtomicDouble

class Cart {

    private var total: AtomicDouble = AtomicDouble(0.0)

    fun addItem(price: Double) {
        total.addAndGet(price)
    }

    fun removeItem(price: Double) {
        total.addAndGet(price * -1)
    }

    fun getTotal() = total.toDouble()

}