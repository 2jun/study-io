package com.aj.study.iostudy.study.designmode;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * @Author: aiJun
 * @Date: 2020-11-10 16:13
 * @ 适配器模式：
 * @Version 1.0
 */
public class AdapterMode {
    public static void main(String args[]) {
        ScoreOperation operation; //针对抽象目标接口编程
        operation = (ScoreOperation) XMLUtil.getBean();
        //读取配置文件，反射生成对象
        int scores[] = {84, 76, 50, 69, 90, 91, 88, 96};
        //定义成绩数组
        int result[];
        int score;
        System.out.println("成绩排序结果：");
        result = operation.sort(scores);
        //遍历输出成绩
        for (int i : scores) {
            System.out.print(i + ",");
        }
        System.out.println();
        System.out.println("查找成绩90：");
        score = operation.search(result, 90);
        if (score != -1) {
            System.out.println("找到成绩90。");
        } else {
            System.out.println("没有找到成绩90。");
        }
        System.out.println("查找成绩92：");
        score = operation.search(result, 92);
        if (score != -1) {
            System.out.println("找到成绩92。");
        } else {
            System.out.println("没有找到成绩92。");
        }
    }
}

//抽象成绩操作类：目标接口
interface ScoreOperation {
    //成绩排序 public int search(int array[],int key); //成绩查找
    int[] sort(int array[]);

    int search(int array[], int key); //成绩查找
}

//快速排序类：适配者
class QuickSort {
    public int[] quickSort(int array[]) {
        sort(array, 0, array.length - 1);
        return array;
    }

    public void sort(int array[], int p, int r) {
        int q;
        if (p < r) {
            q = partition(array, p, r);
            sort(array, p, q - 1);
            sort(array, q + 1, r);
        }
    }

    public int partition(int[] a, int p, int r) {
        int x = a[r];
        int j = p - 1;
        for (int i = p; i <= r - 1; i++) {
            if (a[i] <= x) {
                j++;
                swap(a, j, i);
            }
        }
        swap(a, j + 1, r);
        return j + 1;
    }

    public void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}

class BinarySearch {
    public int binarySearch(int array[], int key) {
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int midVal = array[mid];
            if (midVal < key) {
                low = mid + 1;
            } else if (midVal > key) {
                high = mid - 1;
            } else {
                return 1; //找到元素返回1
            }
        }
        return -1; //未找到元素返回-1
    }
}

//操作适配器：适配器
class OperationAdapter implements ScoreOperation {
    private QuickSort sortObj;
    //定义适配者QuickSort对象
    private BinarySearch searchObj;

    //定义适配者BinarySearch对象
    public OperationAdapter() {
        sortObj = new QuickSort();
        searchObj = new BinarySearch();
    }

    @Override
    public int[] sort(int array[]) {
        //调用适配者类QuickSort的排序方法
        return sortObj.quickSort(array);
    }

    @Override
    public int search(int array[], int key) {
        //调用适配者类BinarySearch的查找方法
        return searchObj.binarySearch(array, key);
    }
}


class XMLUtil {
    //该方法用于从XML配置文件中提取具体类类名，并返回一个实例对象
    public static Object getBean() {
        try {
            //创建文档对象
            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dFactory.newDocumentBuilder();
            Document doc;
            doc = builder.parse(new File("F:\\study-Java\\io-study\\src\\main\\resources\\static\\config.xml"));
            //获取包含类名的文本节点
            NodeList nl = doc.getElementsByTagName("className");
            Node classNode = nl.item(0).getFirstChild();
            String cName = classNode.getNodeValue();
            //通过类名生成实例对象并将其返回
            Class c = Class.forName(cName);
            Object obj = c.newInstance();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}