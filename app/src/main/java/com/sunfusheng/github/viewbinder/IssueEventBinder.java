package com.sunfusheng.github.viewbinder;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sunfusheng.github.R;
import com.sunfusheng.github.model.Event;
import com.sunfusheng.github.util.Utils;
import com.sunfusheng.github.widget.app.AvatarView;
import com.sunfusheng.github.widget.span.SpanTouchTextView;
import com.sunfusheng.multitype.ItemViewBinder;

/**
 * @author sunfusheng on 2018/7/18.
 */
public class IssueEventBinder extends ItemViewBinder<Event, IssueEventBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.item_issue_event, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Event item) {
        Context context = holder.itemView.getContext();
        holder.vAvatar.loadAvatar(item.actor.login, item.actor.avatar_url);
        holder.vIssueDesc.setText(Utils.getIssueDesc(context, item));
        holder.vIssueTitle.setText(item.payload.issue.title);

        TypedValue typedValue = new TypedValue();
        holder.vIssueLayout.getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
        holder.vIssueLayout.setBackgroundResource(Utils.isMyIssue(item.payload.issue) ? R.drawable.drawable_light_red_selector : typedValue.resourceId);
        holder.vIssueLayout.setOnClickListener(v -> {

        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        AvatarView vAvatar;
        RelativeLayout vIssueLayout;
        SpanTouchTextView vIssueDesc;
        TextView vIssueTitle;

        ViewHolder(View view) {
            super(view);
            vAvatar = view.findViewById(R.id.avatar);
            vIssueLayout = view.findViewById(R.id.rl_issue);
            vIssueDesc = view.findViewById(R.id.issue_desc);
            vIssueTitle = view.findViewById(R.id.issue_title);

            vIssueDesc.setMovementMethodDefault();
            vIssueDesc.setNeedForceEventToParent(true);
        }
    }
}
