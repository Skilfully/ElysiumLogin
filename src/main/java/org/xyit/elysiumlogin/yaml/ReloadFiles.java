package org.xyit.elysiumlogin.yaml;

import org.xyit.elysiumlogin.updateStream.var.Var;

import java.io.File;
import java.util.AbstractList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 热重载文件
 */
public class ReloadFiles {

    //要重载的文件列表
    public static AbstractList<String> FileList;


    /**
     * 添加要重载的文件
     * 仅限于GlobalVar中的变量
     * @param VarName 变量名称,例如GlobalVar中的language就填language
     * @param value 文件路径,请使用英文逗号分割
     */
    public static boolean addFile(String VarName,String value){
        String Path = value.replace(",", "\\");
        String add = (VarName + ":" + Path);
        if (isFile(VarName)) {
            return true;
        }
        FileList.add(add);
        return true;
    }

    /**
     * 去除要重载的文件
     * 仅限于GlobalVar中的变量
     * @param VarName 变量名
     */
    public static boolean removeFile(String VarName) {
        if (isFile(VarName)) {
            for (String item : FileList) {
                if (item.contains(VarName)) {
                    FileList.remove(item);
                }
            }
        }
        return true;
    }


    public static boolean isFile(String VarName) {
        return FileList.contains(VarName);
    }
}
