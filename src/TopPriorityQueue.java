import java.util.AbstractQueue;
import java.util.Comparator;
import java.util.Iterator;

/**
 * @param <E>
 * 容量capacity大于0,最多只能添加capacity个,多出的被删除掉.<br/>
 * 删除时根据{@link Comparable}或{@link Comparator} 和 desc
 * 如果comparator==null时使用Comparable<br/>
 * non-thread safe
 * @author chenlb 2008-4-23 下午11:31:06
 */
public class TopPriorityQueue<E> extends AbstractQueue<E> implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private int size;
    public LinkedItem head;
    public LinkedItem last;
    private int top;    //top>0后,容量有限制
    private Comparator<? super E> comparator;
    private boolean desc;    //降序
    
    /**
     * 容量无限制,升序.
     */
    public TopPriorityQueue() {
        this(0, null, false);
    }
    
    /**
     * 容量无限制,
     * @param desc true为降序.
     */
    public TopPriorityQueue(boolean desc) {
        this(0, null, desc);
    }

    /**
     * 容量无限制,升序,用comparator排序
     */
    public TopPriorityQueue(Comparator<? super E> comparator) {
        this(0, comparator, false);
    }
    
    /**
     * 容量无限制,用comparator排序
     * @param desc true为降序
     */
    public TopPriorityQueue(Comparator<? super E> comparator, boolean desc) {
        this(0, comparator, desc);
    }
    

    /**
     * 容量限制为capacity.超出容量限制的,会删除优先级最小(队尾).
     */
    public TopPriorityQueue(int capacity) {
        this(capacity, null, false);
    }
    
    public TopPriorityQueue(int capacity, boolean desc) {
        this(capacity, null, desc);
    }
    
    public TopPriorityQueue(int capacity, Comparator<? super E> comparator) {
        this(capacity, comparator, false);
    }
    
    public TopPriorityQueue(int capacity, Comparator<? super E> comparator, boolean desc) {
        head = new LinkedItem();
        last = head;
        top = capacity;
        this.comparator = comparator;
        this.desc = desc;
    }
    @Override
    public Iterator<E> iterator() {
        // TODO 迭代器
        return new It(head);
    }

    @Override
    public int size() {
        return size;
    }

    public boolean offer(E o) {
        // TODO 添加到适当的位置
        if(o == null) {
            throw new IllegalArgumentException("不能添加值为null的!");
        }
        LinkedItem temp = new LinkedItem(o);
        /*last.next = temp;
        temp.prev = last;*/
        if(last == head) {    //第一个
            last.next = temp;
            temp.prev = last;
            
            last = temp;
        } else {
            LinkedItem tempPrev = last;
            if(comparator != null) {
                while(tempPrev!=null && tempPrev != head) {
                
                    int r = comparator.compare(tempPrev.data, temp.data);
                    if(desc) {
                        r = 0 - r;
                    }
                    if(r == 1) {    //tempPrev > temp
                        //重置,继续向前找
                        tempPrev = tempPrev.prev;
                    } else {    //找到插入的位置
                        break;
                    }
                }
                
            } else if(o instanceof Comparable) {    //用Comparable
                
                while(tempPrev!=null && tempPrev != head) {
                    Comparable<E> tPrev = (Comparable<E>) tempPrev.data;
                    int r = tPrev.compareTo(temp.data);
                    if(desc) {
                        r = 0 - r;
                    }
                    if(r == 1) {    //tempPrev > temp
                        //重置,继续向前找
                        tempPrev = tempPrev.prev;
                    } else {    //找到插入的位置
                        break;
                    }
                }
            }
            if(tempPrev != null) {    //插入
                if(tempPrev == last) {    //后面添加的
                    last = temp;
                }
                
                temp.next = tempPrev.next;
                if(tempPrev.next != null) {
                    tempPrev.next.prev = temp;
                }
                tempPrev.next = temp;
                temp.prev = tempPrev;
            }
        }
        
        size++;
        if(top > 0 && size > top) {    //去掉优先级最小的
            tail();
        }
        return true;
    }

    /**
     * 从栈底去除
     */
    public E tail() {
        if(last == head) {
            return null;
        }
        LinkedItem laster = last;
        last = last.prev;
        last.next = null;
        laster.prev = null;
        size--;
        return laster.data;
    }
    public E getTail() {
        if(last == head) {
            return null;
        }
        LinkedItem laster = last;
        return laster.data;
    }
    public E peek() {
        // TODO 取得栈顶值
        LinkedItem first = head.next;
        if(first != null) {
            return first.data;
        }
        return null;
    }

    
    public E poll() {
        // TODO 从栈顶去除
        LinkedItem first = head.next;
        if(first != null) {
            head.next = first.next;
            if(first.next != null) {
                first.next.prev = head;
            }
            size--;
            return first.data;
        }
        return null;
    }

    private class It implements Iterator<E> {

        LinkedItem curr;
        
        public It(LinkedItem curr) {
            super();
            this.curr = curr;
        }

        
        public boolean hasNext() {
            if(curr != null && curr.next != null) {
                return true;
            }
            return false;
        }

        
        public E next() {
            curr = curr.next;
            E d = curr.data;
            return d;
        }

        
        public void remove() {
            curr.prev.next = curr.next;
            if(curr.next != null) {
                curr.next.prev = curr.prev;
            }
            size--;
        }
        
    }
    
    /**
     * @param <E>
     * 链结点.
     * @author chenlb 2008-5-4 下午04:48:17
     */
    private class LinkedItem {
        LinkedItem prev;
        E data;
        LinkedItem next;
        public LinkedItem() {
        }
        public LinkedItem(E o) {
            this.data = o;
        }
    }
}