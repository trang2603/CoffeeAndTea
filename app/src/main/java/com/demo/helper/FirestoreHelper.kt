package com.demo.helper

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.demo.adapter.GridItemAdapter
import com.demo.adapter.RecycleViewAdapter
import com.demo.model.Item
import com.demo.viewModel.ItemViewModel
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.toObject
import java.util.concurrent.CountDownLatch

object FirestoreHelper {
    private lateinit var sharedViewModel: ItemViewModel
    private val handler: Handler = Handler(Looper.getMainLooper())

    fun setAllFieldToFalse(collectionPath: String) {
        val collectionRef = FirebaseFirestore.getInstance().collection(collectionPath)
        collectionRef
            .get()
            .addOnSuccessListener { query ->
                query.forEach { documentSnapshot ->
                    val documentRef = documentSnapshot.reference
                    documentRef
                        .update("isChecked", false)
                        .addOnSuccessListener {
                        }.addOnFailureListener { e ->
                        }
                }
            }.addOnFailureListener { e ->
            }
    }

    fun fetchDataFromFirebase(
        collection: CollectionReference,
//        items: MutableList<Item>,
        gridItemAdapter: GridItemAdapter,
    ) {
        collection.get().addOnSuccessListener { result ->
            val newItems = mutableListOf<Item>()
            for (document in result) {
                val item = document.toObject(Item::class.java)
                newItems.add(item)
            }
            handler.post {
                gridItemAdapter.submitList(newItems)
            }
        }
        /*collection
            .get()
            .addOnSuccessListener { result ->
                items.clear()
                for (document in result) {
                    val item = document.toObject(Item::class.java)
                    items.add(item)
                }
                // hoãn việc gọi notifyDataChanged cho đến khi recycleView hoaàn tất
                handler.post {
                    gridItemAdapter.notifyDataSetChanged()
                }
            }.addOnFailureListener { exception ->
                exception.printStackTrace()
            }

        // Danh sách để chứa tất cả các item
        val allItems = mutableListOf<Item>()

        // Lắng nghe sự thay đổi dữ liệu trong collection
        collection.addSnapshotListener { snapshot, e ->
            if (e != null) {
                // Xử lý lỗi nếu có
                e.printStackTrace()
                return@addSnapshotListener
            }

            // Xóa dữ liệu cũ
            allItems.clear()

            // Lấy tất cả các item từ snapshot
            snapshot?.forEach { document ->
                val item = document.toObject(Item::class.java)
                allItems.add(item)
            }
            // Cập nhật adapter với danh sách item mới
            gridItemAdapter.submitList(allItems)
            Log.e("hello", "fet")
        }*/
    }

    fun fetchAllDataFromFirestore(
        collections: List<CollectionReference>,
//        items: MutableList<Item>,
        gridItemAdapter: GridItemAdapter,
    ) {
        /*// Tạo một CountDownLatch với số lượng collection
        val latch = CountDownLatch(collections.size)

        // Sử dụng một MutableMap để lưu trữ dữ liệu từ mỗi collection
        val itemListMap = mutableMapOf<String, MutableList<Item>>()

        collections.forEach { collection ->
            val query = collection.whereEqualTo("isChecked", true)
            collection.addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    Log.e("Firestore", "Error getting documents: ", exception)
                    latch.countDown()
                    return@addSnapshotListener
                }

                val itemList = mutableListOf<Item>()
                snapshot?.documents?.forEach { document ->
                    val item = document.toObject(Item::class.java)
                    item?.let { itemList.add(it) }
                }

                // Lưu trữ dữ liệu vào map với tên collection làm key
                itemListMap[collection.path] = itemList

                // Giảm latch sau khi nhận dữ liệu từ collection
                latch.countDown()

                // Khi tất cả các latch đã được giảm xuống 0, cập nhật danh sách tổng hợp
                if (latch.count == 0L) {
                    // Kết hợp tất cả danh sách từ từng collection
                    val combinedItemList = itemListMap.values.flatten()
                    gridItemAdapter.submitList(combinedItemList)
                    Log.e("hello", "fetALL")
                }
            }
        }
        items.clear()

        val tasks =
            collections.map { collection ->
                collection.get()
            }

        Tasks
            .whenAllSuccess<QuerySnapshot>(tasks)
            .addOnSuccessListener { results ->
                results.forEach { result ->
                    for (document in result) {
                        val item = document.toObject(Item::class.java)
                        items.add(item)
                    }
                }
                // Update the UI on the main thread after all tasks are completed
                handler.post {
                    gridItemAdapter.notifyDataSetChanged()
                }
            }.addOnFailureListener { exception ->
                exception.printStackTrace()
            }*/
        val tasks =
            collections.map { collection ->
                collection.get()
            }

        Tasks
            .whenAllSuccess<QuerySnapshot>(tasks)
            .addOnSuccessListener { results ->
                val newItems = mutableListOf<Item>()
                results.forEach { result ->
                    for (document in result) {
                        val item = document.toObject(Item::class.java)
                        newItems.add(item)
                    }
                }
                // Submit the new list to the ListAdapter on the main thread
                handler.post {
                    gridItemAdapter.submitList(newItems)
                }
            }.addOnFailureListener { exception ->
                exception.printStackTrace()
            }
    }

    fun setViewModel(viewModel: ItemViewModel) {
        sharedViewModel = viewModel
        Log.e("hello", "chay setViewModel")
    }

    fun updateItemCheckState(
        collectionPath: String,
        id: String,
        isChecked: Boolean,
    ) {
        val firestore = FirebaseFirestore.getInstance()
        val collectionRef = firestore.collection(collectionPath)
        val updates = hashMapOf<String, Any>("isChecked" to isChecked)

        collectionRef
            .whereEqualTo("id", id)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    document.reference
                        .update(updates)
                        .addOnSuccessListener {
                            sharedViewModel.updateItemCheckState(id, isChecked)
                        }.addOnFailureListener { exception ->
                            Log.e("", "error", exception)
                        }
                }
            }.addOnFailureListener { exception ->
                Log.e("", "loi dong 86", exception)
            }
    }

    fun fetchData(
        collections: List<CollectionReference>,
        recycleViewAdapter: RecycleViewAdapter,
    ) {
        // Tạo một CountDownLatch với số lượng collection
        val latch = CountDownLatch(collections.size)

        // Sử dụng một MutableMap để lưu trữ dữ liệu từ mỗi collection
        val itemListMap = mutableMapOf<String, MutableList<Item>>()

        collections.forEach { collection ->
            val query = collection.whereEqualTo("isChecked", true)
            query.addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    Log.e("Firestore", "Error getting documents: ", exception)
                    latch.countDown()
                    return@addSnapshotListener
                }

                val itemList = mutableListOf<Item>()
                snapshot?.documents?.forEach { document ->
                    val item = document.toObject(Item::class.java)
                    item?.let { itemList.add(it) }
                }

                // Lưu trữ dữ liệu vào map với tên collection làm key
                itemListMap[collection.path] = itemList

                // Giảm latch sau khi nhận dữ liệu từ collection
                latch.countDown()

                // Khi tất cả các latch đã được giảm xuống 0, cập nhật danh sách tổng hợp
                if (latch.count == 0L) {
                    // Kết hợp tất cả danh sách từ từng collection
                    val combinedItemList = itemListMap.values.flatten()
                    recycleViewAdapter.submitList(combinedItemList)
                }
            }
        }
    }
}
