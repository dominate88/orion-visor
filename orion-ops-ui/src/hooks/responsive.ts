import { onMounted, onBeforeMount, onBeforeUnmount } from 'vue';
import { useDebounceFn } from '@vueuse/core';
import { useAppStore } from '@/store';
import { addEventListen, removeEventListen } from '@/utils/event';

const WIDTH = 992;

function queryDevice() {
  const rect = document.body.getBoundingClientRect();
  return rect.width - 1 < WIDTH;
}

export default function useResponsive(immediate?: boolean) {
  const appStore = useAppStore();

  function resizeHandler() {
    if (!document.hidden) {
      const isMobile = queryDevice();
      appStore.updateSettings({
        device: isMobile ? 'mobile' : 'desktop',
        hideMenu: isMobile
      });
    }
  }

  const debounceFn = useDebounceFn(resizeHandler, 100);
  onMounted(() => {
    if (immediate) debounceFn();
  });
  onBeforeMount(() => {
    addEventListen(window, 'resize', debounceFn);
  });
  onBeforeUnmount(() => {
    removeEventListen(window, 'resize', debounceFn);
  });
}
