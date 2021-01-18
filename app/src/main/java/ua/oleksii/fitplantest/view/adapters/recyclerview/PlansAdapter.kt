package ua.oleksii.fitplantest.view.adapters.recyclerview

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.view.clicks
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ActivityScoped
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.plan_list_item.view.*
import ua.oleksii.fitplantest.databinding.PlanListItemBinding
import ua.oleksii.fitplantest.interfaces.OnRecyclerItemClickListener
import ua.oleksii.fitplantest.model.entities.PlanItemEntity
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class PlansAdapter @Inject constructor(
    @ActivityContext private val context: Context
) : RecyclerView.Adapter<PlansAdapter.PlansViewHolder>() {

    var planItems: List<PlanItemEntity>? = null
    var listener: OnRecyclerItemClickListener? = null
    var isWithImages: Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlansViewHolder {
        val binding = PlanListItemBinding.inflate(LayoutInflater.from(parent.context))
        return PlansViewHolder(binding)
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: PlansViewHolder, position: Int) {
        val planItem = planItems?.get(position)
        holder.bind(planItem)
        holder.itemView.planItemContainer.clicks()
            .throttleFirst(1000L, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    listener?.onRecyclerItemClick(position)
                }
            )

    }

    override fun getItemCount(): Int = planItems?.size ?: 0

    inner class PlansViewHolder constructor(private val binding: PlanListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(planItemEntity: PlanItemEntity?) {
            binding.needShowImages = isWithImages
            binding.planItem = planItemEntity
            binding.executePendingBindings()
        }
    }
}
