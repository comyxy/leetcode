package sort;


import javax.annotation.Nullable;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @date 2021/7/17 0:07
 * from spring
 */
public class StopWatch {
    private final String id;

    private boolean keepTaskList = true;

    private final List<TaskInfo> taskList = new LinkedList<>();

    private long startTimeNanos;

    @Nullable
    private String currentTaskName;

    @Nullable
    private TaskInfo lastTaskInfo;

    private int taskCount;

    private long totalTimeNanos;

    public StopWatch() {
        this("");
    }

    public StopWatch(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setKeepTaskList(boolean keepTaskList) {
        this.keepTaskList = keepTaskList;
    }

    public void start() throws IllegalStateException {
        start("");
    }

    public void start(String taskName) throws IllegalStateException {
        if(this.currentTaskName != null) {
            throw new IllegalStateException("StopWatch已经运行");
        }
        this.currentTaskName = taskName;
        this.startTimeNanos = System.nanoTime();
    }

    public void stop() throws IllegalStateException {
        if(this.currentTaskName == null) {
            throw new IllegalStateException("StopWatch还未运行");
        }
        long lastTime = System.nanoTime() - this.startTimeNanos;
        this.totalTimeNanos += lastTime;
        this.lastTaskInfo = new TaskInfo(this.currentTaskName, lastTime);
        if(this.keepTaskList) {
            this.taskList.add(this.lastTaskInfo);
        }
        this.taskCount++;
        this.currentTaskName = null;
    }

    public boolean isRunning() {
        return this.currentTaskName != null;
    }

    @Nullable
    public String currentTaskName() {
        return this.currentTaskName;
    }

    public long getLastTaskTimeNanos() throws IllegalStateException {
        if(this.lastTaskInfo == null) {
            throw new IllegalStateException("没有运行过的任务");
        }
        return this.lastTaskInfo.getTimeNanos();
    }

    public long getLastTaskTimeMillis() throws IllegalStateException {
        if(this.lastTaskInfo == null) {
            throw new IllegalStateException("没有运行过的任务");
        }
        return this.lastTaskInfo.getTimeMillis();
    }

    public long getLastTaskTimeSeconds() throws IllegalStateException {
        if(this.lastTaskInfo == null) {
            throw new IllegalStateException("没有运行过的任务");
        }
        return this.lastTaskInfo.getTimeSeconds();
    }

    public String getLastTaskName() throws IllegalStateException {
        if(this.lastTaskInfo == null) {
            throw new IllegalStateException("没有运行过的任务");
        }
        return this.lastTaskInfo.getTaskName();
    }

    public TaskInfo getLastTaskInfo() throws IllegalStateException {
        if(this.lastTaskInfo == null) {
            throw new IllegalStateException("没有运行过的任务");
        }
        return this.lastTaskInfo;
    }

    public long getTotalTimeNanos() {
        return this.totalTimeNanos;
    }

    public long getTotalTimeMillis() {
        return nanoToMillis(this.totalTimeNanos);
    }

    public long getTotalTimeSeconds() {
        return nanoToSeconds(this.totalTimeNanos);
    }

    public int getTaskCount() {
        return this.taskCount;
    }

    public TaskInfo[] getTaskInfo() throws IllegalStateException {
        if(!this.keepTaskList) {
            throw new IllegalStateException("任务信息没有保留");
        }
        return this.taskList.toArray(new TaskInfo[0]);
    }

    public String shortSummary() {
        return "StopWatch '" + getId() + "': running time = " + getTotalTimeNanos() + " ns";
    }

    public String prettyPrint() {
        StringBuilder sb = new StringBuilder(shortSummary());
        sb.append('\n');
        if (!this.keepTaskList) {
            sb.append("No task info kept");
        }
        else {
            sb.append("---------------------------------------------\n");
            sb.append("ns         %     Task name\n");
            sb.append("---------------------------------------------\n");
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMinimumIntegerDigits(9);
            nf.setGroupingUsed(false);
            NumberFormat pf = NumberFormat.getPercentInstance();
            pf.setMinimumIntegerDigits(3);
            pf.setGroupingUsed(false);
            for (TaskInfo task : getTaskInfo()) {
                sb.append(nf.format(task.getTimeNanos())).append("  ");
                sb.append(pf.format((double) task.getTimeNanos() / getTotalTimeNanos())).append("  ");
                sb.append(task.getTaskName()).append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(shortSummary());
        if (this.keepTaskList) {
            for (TaskInfo task : getTaskInfo()) {
                sb.append("; [").append(task.getTaskName()).append("] took ").append(task.getTimeNanos()).append(" ns");
                long percent = Math.round(100.0 * task.getTimeNanos() / getTotalTimeNanos());
                sb.append(" = ").append(percent).append("%");
            }
        }
        else {
            sb.append("; no task info kept");
        }
        return sb.toString();
    }

    private static long nanoToMillis(long duration) {
        return TimeUnit.NANOSECONDS.toMicros(duration);
    }

    private static long nanoToSeconds(long duration) {
        return TimeUnit.NANOSECONDS.toSeconds(duration);
    }

    public static final class TaskInfo {
        private final String taskName;

        private final long timeNanos;

        public TaskInfo(String taskName, long timeNanos) {
            this.taskName = taskName;
            this.timeNanos = timeNanos;
        }

        public String getTaskName() {
            return taskName;
        }

        public long getTimeNanos() {
            return timeNanos;
        }

        public long getTimeMillis() {
            return nanoToMillis(timeNanos);
        }

        public long getTimeSeconds() {
            return nanoToSeconds(timeNanos);
        }
    }
}
