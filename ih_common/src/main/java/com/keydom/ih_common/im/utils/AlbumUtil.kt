package com.keydom.ih_common.im.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.support.v4.app.Fragment
import com.keydom.ih_common.R
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

//相册工具类
/**
 *图片回调位置：onActivityResult
 */
/*
if (resultCode == RESULT_OK) {
    switch (requestCode) {
        case PictureConfig.CHOOSE_REQUEST:
        // 图片选择结果回调
        List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
        // 1.media.getPath(); 为原图path
        // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
        // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
        // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
        adapter.setList(selectList);
        adapter.notifyDataSetChanged();
        break;
    }
}
*/
/**
 * 跳转图库-单选照片
 */
fun Activity.gotoSinglePhotoPicker(isCamera: Boolean = true, isCrop: Boolean = true, isCircle: Boolean = false, aspectRatio: WithAspectRatio = WithAspectRatio(1, 1), isCompress: Boolean = true, list: List<LocalMedia>? = null, requestCode: Int = PictureConfig.CHOOSE_REQUEST) {
    gotoPhotoPicker(this, true, 2, isCamera, isCrop, isCircle, aspectRatio, isCompress, list, requestCode)
}

/**
 * 跳转图库-单选照片
 */
fun Fragment.gotoSinglePhotoPicker(isCamera: Boolean = true, isCrop: Boolean = true, isCircle: Boolean = false, aspectRatio: WithAspectRatio = WithAspectRatio(1, 1), isCompress: Boolean = true, list: List<LocalMedia>? = null, requestCode: Int = PictureConfig.CHOOSE_REQUEST) {
    gotoPhotoPicker(null, true, 2, isCamera, isCrop, isCircle, aspectRatio, isCompress, list, requestCode, this)
}

/**
 * 跳转图库-多选择照片
 */
fun Activity.gotoMultiplePhotoPicker(maxNum: Int = 9, isCamera: Boolean = true, isCrop: Boolean = true, isCircle: Boolean = false, aspectRatio: WithAspectRatio = WithAspectRatio(1, 1), isCompress: Boolean = true, list: List<LocalMedia>? = null, requestCode: Int = PictureConfig.CHOOSE_REQUEST) {
    gotoPhotoPicker(this, false, maxNum, isCamera, isCrop, isCircle, aspectRatio, isCompress, list, requestCode)
}

/**
 * 跳转图库-多选择照片
 */
fun Fragment.gotoMultiplePhotoPicker(maxNum: Int = 9, isCamera: Boolean = true, isCrop: Boolean = true, isCircle: Boolean = false, aspectRatio: WithAspectRatio = WithAspectRatio(1, 1), isCompress: Boolean = true, list: List<LocalMedia>? = null, requestCode: Int = PictureConfig.CHOOSE_REQUEST) {
    gotoPhotoPicker(null, false, maxNum, isCamera, isCrop, isCircle, aspectRatio, isCompress, list, requestCode, this)
}

/**
 * 图片预览
 */
fun Activity.previewPhotos(position: Int = 0, selectList: List<LocalMedia>) {
    PictureSelector.create(this).themeStyle(R.style.picture_default_style).openExternalPreview(position, selectList)
}

/**
 * 图片预览
 */
fun Fragment.previewPhotos(position: Int = 0, selectList: List<LocalMedia>) {
    PictureSelector.create(this).themeStyle(R.style.picture_default_style).openExternalPreview(position, selectList)
}

/**
 * 跳转拍照
 */
fun Activity.gotoCamera(isCrop: Boolean = true, isCircle: Boolean = false, aspectRatio: WithAspectRatio = WithAspectRatio(1, 1), isCompress: Boolean = true, requestCode: Int = PictureConfig.CHOOSE_REQUEST) {
    gotoPhotoPicker(this, true, 1, false, isCrop, isCircle, aspectRatio, isCompress, null, requestCode, openCamera = true)
}

/**
 * 跳转拍照
 */
fun Fragment.gotoCamera(isCrop: Boolean = true, isCircle: Boolean = false, aspectRatio: WithAspectRatio = WithAspectRatio(1, 1), isCompress: Boolean = true, requestCode: Int = PictureConfig.CHOOSE_REQUEST) {
    gotoPhotoPicker(null, true, 1, false, isCrop, isCircle, aspectRatio, isCompress, null, requestCode, openCamera = true, fragment = this)
}

@JvmOverloads
fun gotoPhotoPicker(activity: Activity?, isSingle: Boolean, maxNum: Int, isCamera: Boolean, isCrop: Boolean, isCircle: Boolean, aspectRatio: WithAspectRatio, isCompress: Boolean, list: List<LocalMedia>?, requestCode: Int = PictureConfig.CHOOSE_REQUEST, fragment: Fragment? = null, openCamera: Boolean = false) {
//    val selector = activity?.let { PictureSelector.create(activity) }
//            ?: let { PictureSelector.create(activity) }
//
    val selector: PictureSelector = when {
        activity != null -> PictureSelector.create(activity)
        fragment != null -> PictureSelector.create(fragment)
        else -> return
    }
    val s = if (!openCamera) selector.openGallery(PictureMimeType.ofImage())
    else selector.openCamera(PictureMimeType.ofVideo()).recordVideoSecond(10)
    if (!isSingle) {
        s.maxSelectNum(maxNum)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
    }
    s.theme(R.style.picture_default_style)
            .selectionMode(if (isSingle) PictureConfig.SINGLE else PictureConfig.MULTIPLE)
            .previewImage(true)// 是否可预览图片
            .isCamera(isCamera)// 是否显示拍照按钮
            .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
            .isZoomAnim(true)// 图片列表点击缩放效果
            .sizeMultiplier(0.5f)// glide 加载图片大小
            .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
            .enableCrop(isCrop)// 是否裁剪
            .compress(isCompress)//压缩
            .withAspectRatio(aspectRatio.width, aspectRatio.height)//裁剪比例
            .hideBottomControls(true)// 是否显示uCrop工具栏
            .isGif(false)// 是否显示gif图片
//            .compressSavePath(PathUtils.getCache("CompressImage"))//压缩图片保存地址
            .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
            .circleDimmedLayer(isCircle)// 是否圆形裁剪
            .showCropFrame(!isCircle)// 是否显示裁剪矩形边框
            .showCropGrid(!isCircle)// 是否显示裁剪矩形网格
            .openClickSound(false)// 是否开启点击声音
            .selectionMedia(list)// 是否传入已选图片
            .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验
            .cropCompressQuality(80)// 裁剪压缩质量
            .minimumCompressSize(100)// 小于100kb的图片不压缩
            .synOrAsy(true)//同步true或异步false 压缩
            .rotateEnabled(true) // 裁剪是否可旋转图片
            .scaleEnabled(true)// 裁剪是否可放大缩小图片
            .isDragFrame(true)// 是否可拖动裁剪框(固定)
            .forResult(requestCode)//结果回调onActivityResult code
}


data class WithAspectRatio(var width: Int, var height: Int)

/**
 * 保存图片到相册并刷新
 */
fun Context.saveImageToGallery(filePath: String, fileName: String, bmp: Bitmap): File {
    // 首先保存图片
    val appDir = File(filePath)
    if (!appDir.exists()) {
        appDir.mkdirs()
    }
    val file = File(appDir, fileName)
    try {
        val fos = FileOutputStream(file)
        bmp.compress(Bitmap.CompressFormat.PNG, 100, fos)
        fos.flush()
        fos.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }

    // 其次把文件插入到系统图库
    try {
        MediaStore.Images.Media.insertImage(contentResolver, file.absolutePath, fileName, null)
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    }

    // 最后通知图库更新
    sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)))
    return file
}