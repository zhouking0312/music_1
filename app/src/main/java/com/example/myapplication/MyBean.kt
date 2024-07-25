package com.example.myapplication

data class MyBean(val name: String, val age: Int) {
    companion object {
        private var instance = mutableListOf<MyBean>()

        fun addItem(item: MyBean) {
            instance.add(item)
        }

        fun removeItem(item: MyBean) {
            instance.remove(item)
        }

        fun getAllItems(): List<MyBean> {
            return instance.toList()
        }
    }
}

