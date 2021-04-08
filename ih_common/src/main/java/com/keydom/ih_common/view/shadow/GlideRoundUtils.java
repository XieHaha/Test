package com.keydom.ih_common.view.shadow;

import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by leo
 * on 2020/8/3.
 */
class GlideRoundUtils {
    public static void setRoundCorner(final View view, final Drawable resourceId,
                                      final float cornerDipValue) {
//
//        if (cornerDipValue == 0) {
//            if (view.getMeasuredWidth() == 0 && view.getMeasuredHeight() == 0) {
//                view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//                    @Override
//                    public void onLayoutChange(View v, int left, int top, int right, int bottom,
//                                               int oldLeft, int oldTop, int oldRight,
//                                               int oldBottom) {
//                        view.removeOnLayoutChangeListener(this);
//                        Glide.with(view)
//                                .asDrawable()
//                                .load(resourceId)
//                                .transform(new CenterCrop())
//                                .override(view.getMeasuredWidth(), view.getMeasuredHeight())
//                                .into(new CustomTarget<Drawable>() {
//                                    @Override
//                                    public void onResourceReady(@NonNull Drawable resource,
//                                                                @Nullable Transition<?
//                                                                        super Drawable> transition) {
//                                        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
//                                            view.setBackgroundDrawable(resource);
//                                        } else {
//                                            view.setBackground(resource);
//                                        }
//
//                                    }
//
//                                    @Override
//                                    public void onLoadCleared(@Nullable Drawable placeholder) {
//
//                                    }
//                                });
//                    }
//                });
//            } else {
//                Glide.with(view)
//                        .asDrawable()
//                        .load(resourceId)
//                        .transform(new CenterCrop())
//                        .override(view.getMeasuredWidth(), view.getMeasuredHeight())
//                        .into(new CustomTarget<Drawable>() {
//                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//                            @Override
//                            public void onResourceReady(@NonNull Drawable resource,
//                                                        @Nullable Transition<? super Drawable> transition) {
//                                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
//                                    view.setBackgroundDrawable(resource);
//                                } else {
//                                    view.setBackground(resource);
//                                }
//                            }
//
//                            @Override
//                            public void onLoadCleared(@Nullable Drawable placeholder) {
//
//                            }
//                        });
//            }
//
//        } else {
//
//            if (view.getMeasuredWidth() == 0 && view.getMeasuredHeight() == 0) {
//                view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//                    @Override
//                    public void onLayoutChange(View v, int left, int top, int right, int bottom,
//                                               int oldLeft, int oldTop, int oldRight,
//                                               int oldBottom) {
//                        view.removeOnLayoutChangeListener(this);
//                        Glide.with(view)
//                                .load(resourceId)
//                                .transform(new CenterCrop(),
//                                        new RoundedCorners((int) cornerDipValue))
//                                .override(view.getMeasuredWidth(), view.getMeasuredHeight())
//                                .into(new CustomTarget<Drawable>() {
//                                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//                                    @Override
//                                    public void onResourceReady(@NonNull Drawable resource,
//                                                                @Nullable Transition<?
//                                                                        super Drawable> transition) {
//                                        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
//                                            view.setBackgroundDrawable(resource);
//                                        } else {
//                                            view.setBackground(resource);
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onLoadCleared(@Nullable Drawable placeholder) {
//
//                                    }
//                                });
//                    }
//                });
//            } else {
//                Glide.with(view)
//                        .load(resourceId)
//                        .transform(new CenterCrop(), new RoundedCorners((int) cornerDipValue))
//                        .override(view.getMeasuredWidth(), view.getMeasuredHeight())
//                        .into(new CustomTarget<Drawable>() {
//                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//                            @Override
//                            public void onResourceReady(@NonNull Drawable resource,
//                                                        @Nullable Transition<? super Drawable> transition) {
//                                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
//                                    view.setBackgroundDrawable(resource);
//                                } else {
//                                    view.setBackground(resource);
//                                }
//                            }
//
//                            @Override
//                            public void onLoadCleared(@Nullable Drawable placeholder) {
//
//                            }
//                        });
//            }
//
//        }
//
//    }
//
//
//    public static void setCorners(final View view, final Drawable resourceId,
//                                  final float leftTop_corner, final float leftBottom_corner,
//                                  final float rightTop_corner, final float rightBottom_corner) {
//        if (leftTop_corner == 0 && leftBottom_corner == 0 && rightTop_corner == 0 && rightBottom_corner == 0) {
//            if (view.getMeasuredWidth() == 0 && view.getMeasuredHeight() == 0) {
//                view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//                    @Override
//                    public void onLayoutChange(View v, int left, int top, int right, int bottom,
//                                               int oldLeft, int oldTop, int oldRight,
//                                               int oldBottom) {
//                        view.removeOnLayoutChangeListener(this);
//                        Glide.with(view)
//                                .load(resourceId)
//                                .override(view.getMeasuredWidth(), view.getMeasuredHeight())
//                                .into(new CustomTarget<Drawable>() {
//                                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//                                    @Override
//                                    public void onResourceReady(@NonNull Drawable resource,
//                                                                @Nullable Transition<?
//                                                                        super Drawable> transition) {
//                                        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
//                                            view.setBackgroundDrawable(resource);
//                                        } else {
//                                            view.setBackground(resource);
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onLoadCleared(@Nullable Drawable placeholder) {
//
//                                    }
//                                });
//                    }
//                });
//            } else {
//                Glide.with(view)
//                        .load(resourceId)
//                        .override(view.getMeasuredWidth(), view.getMeasuredHeight())
//                        .into(new CustomTarget<Drawable>() {
//                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//                            @Override
//                            public void onResourceReady(@NonNull Drawable resource,
//                                                        @Nullable Transition<? super Drawable> transition) {
//                                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
//                                    view.setBackgroundDrawable(resource);
//                                } else {
//                                    view.setBackground(resource);
//                                }
//                            }
//
//                            @Override
//                            public void onLoadCleared(@Nullable Drawable placeholder) {
//
//                            }
//                        });
//            }
//
//        } else {
//            if (view.getMeasuredWidth() == 0 && view.getMeasuredHeight() == 0) {
//                view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//                    @Override
//                    public void onLayoutChange(View v, int left, int top, int right, int bottom,
//                                               int oldLeft, int oldTop, int oldRight,
//                                               int oldBottom) {
//                        view.removeOnLayoutChangeListener(this);
//                        GlideRoundTransform transform = new GlideRoundTransform(view.getContext()
//                                , leftTop_corner, leftBottom_corner, rightTop_corner,
//                                rightBottom_corner);
//                        Glide.with(view)
//                                .load(resourceId)
//                                .transform(transform)
//                                .override(view.getMeasuredWidth(), view.getMeasuredHeight())
//                                .into(new CustomTarget<Drawable>() {
//                                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//                                    @Override
//                                    public void onResourceReady(@NonNull Drawable resource,
//                                                                @Nullable Transition<?
//                                                                        super Drawable> transition) {
//                                        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
//                                            view.setBackgroundDrawable(resource);
//                                        } else {
//                                            view.setBackground(resource);
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onLoadCleared(@Nullable Drawable placeholder) {
//
//                                    }
//                                });
//                    }
//                });
//            } else {
//
//                GlideRoundTransform transform = new GlideRoundTransform(view.getContext(),
//                        leftTop_corner, leftBottom_corner, rightTop_corner, rightBottom_corner);
//                Glide.with(view)
//                        .load(resourceId)
//                        .transform(transform)
//                        .override(view.getMeasuredWidth(), view.getMeasuredHeight())
//                        .into(new CustomTarget<Drawable>() {
//                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//                            @Override
//                            public void onResourceReady(@NonNull Drawable resource,
//                                                        @Nullable Transition<? super Drawable> transition) {
//                                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
//                                    view.setBackgroundDrawable(resource);
//                                } else {
//                                    view.setBackground(resource);
//                                }
//                            }
//
//                            @Override
//                            public void onLoadCleared(@Nullable Drawable placeholder) {
//
//                            }
//                        });
//
//            }
//
//        }
//
    }

}
