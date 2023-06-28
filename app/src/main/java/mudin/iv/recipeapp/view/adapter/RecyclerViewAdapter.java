package mudin.iv.recipeapp.view.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import mudin.iv.recipeapp.utils.NavigationManager;
import mudin.iv.recipeapp.utils.PaperDbManager;
import mudin.iv.recipeapp.R;
import mudin.iv.recipeapp.model.Recipe;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity context;
    ArrayList<Recipe> userArrayList;

    //adapter for todo lsit

    public RecyclerViewAdapter(Activity context, ArrayList<Recipe> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.card_item,parent,false);
        return new RecyclerViewViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        //bind view here with current position object from the arraylist
        Recipe recipe = userArrayList.get(position);
        RecyclerViewViewHolder viewHolder= (RecyclerViewViewHolder) holder;

        String stepTitle = "Steps:\n";
        String ingredientTitle = "Ingredient:\n";

        viewHolder.tvTitle.setText(recipe.getTitle());
        viewHolder.tvType.setText(recipe.getType());
        viewHolder.tvIngredients.setText(stepTitle+recipe.getIngredient());
        viewHolder.tvSteps.setText(ingredientTitle+ recipe.getSteps());
        String imageLink = recipe.getImageLink().equals("") ? "https://previews.123rf.com/images/akeeris/akeeris1306/akeeris130600107/20500993-meal-time-full-round-table-with-colorful-food-in-restaurant.jpg" : recipe.getImageLink();

        Picasso
                .get()
                .load(imageLink)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(viewHolder.ivImage)
        ;

    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    class RecyclerViewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle;
        TextView tvSteps;
        TextView tvIngredients;
        TextView tvType;
        ImageView ivImage;

        //the base of viewholder is identified at here

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvType = itemView.findViewById(R.id.tvType);
            tvIngredients = itemView.findViewById(R.id.tvIngredient);
            tvSteps = itemView.findViewById(R.id.tvStep);
            ivImage = itemView.findViewById(R.id.ivImage);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            new NavigationManager().toFormActivity(view.getContext(),String.valueOf(getAdapterPosition()));
        }
    }
}
