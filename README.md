# StarTracker_101
A simple "lost in space" Star tracking algorithm, based in :geometric hashing.

Two sets of 200 the same points were constructed (St0, St1).
each of the sets were shuffeled and then randomly reduced to 100 points.
The original (St0) set is shown in blue.
The second set (St1) was also moved (translated), rotated, and scaled (shown in red).
The algorithm was able to find the ~25% overlapping (transfomed) points using RANSAC like method with geometric hashing.

In blue the original 
![Screenshot 2023-05-28 at 0 04 08](https://github.com/benmoshe/StarTracker_101/assets/1771658/ce98fc2d-26da-4c8d-b36e-6221af17a9ed)
