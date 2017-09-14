package com.enjoy.base;

import java.util.ArrayList;
import java.util.List;
import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by hyc on 2017/4/10 20:30
 */

public class BaseBmob<T extends BmobObject> extends BmobQuery<T>{

    public interface OnAddDataListener{
        void successful(String objectId);
        void fail(String error);
    }

    public interface OnAddDataByListListener{
        void successful(List<String> objectIds);
        void partFail(int i,String error);
        void fail(String error);
    }

    public interface OnUpdateDataListener{
        void successful();
        void fail(String error);
    }

    public interface OnUpdataDataByListListener{
        void successful();
        void partFail(int index,String error);
        void fail(String error);
    }




    /**
     * 添加一个的对象
     * @param bmobObject
     * @param onDealDataListener
     */
    public static void addData(BmobObject bmobObject, final OnAddDataListener onDealDataListener){
        bmobObject.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    onDealDataListener.successful(s);
                }else {
                    onDealDataListener.fail(e.getMessage());
                }
            }
        });
    }

    /**
     * 获取一个对象的数据
     * @param objectId
     * @param onGetDataListener
     */
    public void getData(String objectId, final com.enjoy.base.OnGetDataListener<T> onGetDataListener){
        getObject(objectId,onGetDataListener);
    }


    /**
     * 更新一个对象的数据
     * @param bmobObject
     * @param onUpdateDataListener
     */
    public static void upData(BmobObject bmobObject, final OnUpdateDataListener onUpdateDataListener){
        bmobObject.update(bmobObject.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                    onUpdateDataListener.successful();
                }else {
                    onUpdateDataListener.fail(e.getMessage());
                }
            }
        });
    }

    /**
     * 删除一个对象的数据
     * @param bmobObject
     * @param onUpdateDataListener
     */
    public static void deleteData(BmobObject bmobObject,final OnUpdateDataListener onUpdateDataListener){
        bmobObject.delete(bmobObject.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                    onUpdateDataListener.successful();
                }else {
                    onUpdateDataListener.fail(e.getMessage());
                }
            }
        });
    }

    /**
     * 批量添加数据
     * @param bmobObjects
     * @param onAddDataByListListener
     */
    public static void addDataByList(List<BmobObject> bmobObjects, final OnAddDataByListListener onAddDataByListListener){
        new BmobBatch().insertBatch(bmobObjects).doBatch(new QueryListListener<BatchResult>() {

            @Override
            public void done(List<BatchResult> o, BmobException e) {
                if(e==null){
                    List<String> objects=new ArrayList<String>();
                    for (BatchResult batchResult:o){
                        if (!batchResult.isSuccess()){
                            onAddDataByListListener.partFail(o
                                    .indexOf(batchResult),batchResult.getError().getMessage());
                            return;
                        }
                        objects.add(batchResult.getObjectId());
                    }
                    onAddDataByListListener.successful(objects);
                }else{
                    onAddDataByListListener.fail(e.getMessage());
                }
            }
        });
    }

    /**
     * 批量更新数据
     * @param bmobObjects
     * @param onUpdataDataByListListener
     */
    public static void upDataByList(List<BmobObject> bmobObjects,final OnUpdataDataByListListener onUpdataDataByListListener){
        new BmobBatch().updateBatch(bmobObjects).doBatch(new QueryListListener<BatchResult>() {

            @Override
            public void done(List<BatchResult> o, BmobException e) {
                if(e==null){
                    for (BatchResult batchResult:o){
                        if (!batchResult.isSuccess()){
                            onUpdataDataByListListener.partFail(o
                                    .indexOf(batchResult),batchResult.getError().getMessage());
                            return;
                        }
                    }
                    onUpdataDataByListListener.successful();
                }else{
                   onUpdataDataByListListener.fail(e.getMessage());
                }
            }
        });
    }

    /**
     * 批量删除数据
     * @param bmobObjects
     * @param onUpdataDataByListListener
     */
    public static void deleteDataByList(List<BmobObject>bmobObjects, final OnUpdataDataByListListener onUpdataDataByListListener){
        new BmobBatch().deleteBatch(bmobObjects).doBatch(new QueryListListener<BatchResult>() {

            @Override
            public void done(List<BatchResult> o, BmobException e) {
                if(e==null){
                    for (BatchResult batchResult:o){
                        if (!batchResult.isSuccess()){
                            onUpdataDataByListListener.partFail(o
                                    .indexOf(batchResult),batchResult.getError().getMessage());
                            return;
                        }
                    }
                    onUpdataDataByListListener.successful();
                }else{
                    onUpdataDataByListListener.fail(e.getMessage());
                }
            }
        });
    }

}
