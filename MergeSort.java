import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * MergeSort implementation for sorting messages and user lists.
 * Provides both generic sorting and binary search capabilities.
 */
public class MergeSort {
    
    /**
     * Sort an array of strings using merge sort
     */
    public static String[] sort(String[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }
        
        String[] temp = new String[array.length];
        mergeSort(array, temp, 0, array.length - 1);
        return array;
    }
    
    /**
     * Merge sort implementation
     */
    private static void mergeSort(String[] array, String[] temp, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(array, temp, left, mid);
            mergeSort(array, temp, mid + 1, right);
            merge(array, temp, left, mid, right);
        }
    }
    
    /**
     * Merge two sorted subarrays
     */
    private static void merge(String[] array, String[] temp, int left, int mid, int right) {
        // Copy elements to temp array
        for (int i = left; i <= right; i++) {
            temp[i] = array[i];
        }
        
        int i = left;
        int j = mid + 1;
        int k = left;
        
        // Merge back to original array
        while (i <= mid && j <= right) {
            if (temp[i].compareToIgnoreCase(temp[j]) <= 0) {
                array[k++] = temp[i++];
            } else {
                array[k++] = temp[j++];
            }
        }
        
        // Copy remaining elements from left half
        while (i <= mid) {
            array[k++] = temp[i++];
        }
        
        // Copy remaining elements from right half (if any)
        while (j <= right) {
            array[k++] = temp[j++];
        }
    }
    
    /**
     * Binary search on a sorted array
     */
    public static int binarySearch(String[] sortedArray, String target) {
        if (sortedArray == null || target == null) {
            return -1;
        }
        
        int left = 0;
        int right = sortedArray.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int comparison = sortedArray[mid].compareToIgnoreCase(target);
            
            if (comparison == 0) {
                return mid;
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return -1; // Not found
    }
    
    /**
     * Search for messages containing a keyword (partial match)
     */
    public static List<String> searchMessages(String[] messages, String keyword) {
        List<String> results = new ArrayList<>();
        
        if (messages == null || keyword == null) {
            return results;
        }
        
        String lowerKeyword = keyword.toLowerCase();
        for (String message : messages) {
            if (message != null && message.toLowerCase().contains(lowerKeyword)) {
                results.add(message);
            }
        }
        
        return results;
    }
    
    /**
     * Search for users matching a name (partial match)
     */
    public static List<String> searchUsers(List<String> users, String keyword) {
        List<String> results = new ArrayList<>();
        
        if (users == null || keyword == null) {
            return results;
        }
        
        String lowerKeyword = keyword.toLowerCase();
        for (String user : users) {
            if (user != null && user.toLowerCase().contains(lowerKeyword)) {
                results.add(user);
            }
        }
        
        return results;
    }
    
    /**
     * Sort a list of strings
     */
    public static List<String> sortList(List<String> list) {
        if (list == null || list.size() <= 1) {
            return list;
        }
        
        String[] array = list.toArray(new String[list.size()]);
        sort(array);
        List<String> sortedList = new ArrayList<>();
        for (String s : array) {
            sortedList.add(s);
        }
        return sortedList;
    }
}
